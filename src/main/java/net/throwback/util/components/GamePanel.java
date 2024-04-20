package net.throwback.util.components;

import net.throwback.objects.TileObject;
import net.throwback.objects.entity.MovementType;
import net.throwback.objects.entity.Player;
import net.throwback.objects.objectId.ObjectId;
import net.throwback.util.Session;
import net.throwback.worlds.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener, ComponentListener {
    private static GamePanel instance;

    private final Thread thread;
    private final int fps;
    private int cf;

    private final int zoom;

    private int cameraX, cameraY;
    private int maxTop, maxBottom, maxLeft, maxRight;

    private Session session;
    private World world;
    private Player player;

    private GamePanel() {
        thread = new Thread(this);
        fps = 60;
        cf = 1;

        zoom = 40;

        cameraX = -1;
        cameraY = -1;

        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addComponentListener(this);
        setLayout(null);
    }

    private void update() {
        if(cameraX == -1 && cameraY == -1) {
            cameraX = getWidth() / 2;
            cameraY = getHeight() / 2;
        }

        maxTop = getHeight() / 3;
        maxBottom = getHeight() - maxTop;
        maxLeft = getWidth() / 3;
        maxRight = getWidth() - maxLeft;

        setFocusable(true);
        requestFocus();
        revalidate();
        repaint();
    }

    public static GamePanel getInstance() {
        if(instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    public void start() {
        if(session != null) {
            thread.start();
            session.start();
        }
    }

    public void stop() {
        thread.interrupt();
        session.stop();
    }

    public void setup(Session session) {
        if(thread.isAlive()) {
            stop();
        }
        this.session = session;
        world = session.getWorlds()[0];
        player = session.getPlayer();

        start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(session != null) {
            TileObject[][] grid = world.getGrid();
            grid[80][80] = new TileObject(80, 80, ObjectId.EXAMPLE);
            grid[80][0] = new TileObject(80, 0, ObjectId.EXAMPLE);

            int startX = (int) player.getX() - (getWidth() / (2 * zoom));
            int endX = (int) player.getX() + (getWidth() / (2 * zoom)) + 1;
            int startY = (int) player.getY() - (getHeight() / (2 * zoom));
            int endY = (int) player.getY() + (getHeight() / (2 * zoom)) + 1;

            for(int x = startX - 1; x <= endX + 1; x++) {
                for(int y = startY - 1; y <= endY + 1; y++) {
                    int extraX, extraY;
                    if((getWidth() / 2 - zoom / 2) % zoom < zoom / 2) {
                        extraX = (getWidth() / 2 - zoom / 2) % zoom;
                    } else {
                        extraX = ((getWidth() / 2 - zoom / 2) % zoom) - zoom;
                    }

                    if((getHeight() / 2 - zoom / 2) % zoom < zoom / 2) {
                        extraY = (getHeight() / 2 - zoom / 2) % zoom;
                    } else {
                        extraY = ((getHeight() / 2 - zoom / 2) % zoom) - zoom;
                    }

                    int screenX = (int) ((x - startX) * zoom - Math.ceil((player.getX() % 1) * zoom)) + extraX;
                    int screenY = (int) ((y - startY) * zoom - Math.ceil((player.getY() % 1) * zoom)) + extraY;

                    Image texture = world.getBorderTile().getTextures()[0];
                    if(x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight()) {
                        texture = grid[x][y].getTextures()[0];
                    }

                    g2.drawImage(texture, screenX, screenY, zoom, zoom, this);
                }
            }

            int ovalX = getWidth() / 2 - zoom / 2;
            int ovalY = getHeight() / 2 - zoom / 2;
            g2.setColor(Color.RED);
            g2.drawRect(ovalX, ovalY, zoom, zoom);
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while(thread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                cf = (cf != 60) ? cf + 1 : 1;
                delta--;
            }

            if(timer >= 1000000000) {
                timer = 0;
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> player.startMoving(MovementType.UP);
            case KeyEvent.VK_A -> player.startMoving(MovementType.LEFT);
            case KeyEvent.VK_S -> player.startMoving(MovementType.DOWN);
            case KeyEvent.VK_D -> player.startMoving(MovementType.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> player.stopMoving(MovementType.UP);
            case KeyEvent.VK_A -> player.stopMoving(MovementType.LEFT);
            case KeyEvent.VK_S -> player.stopMoving(MovementType.DOWN);
            case KeyEvent.VK_D -> player.stopMoving(MovementType.RIGHT);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        cameraX = getWidth() / 2;
        cameraY = getHeight() / 2;
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
