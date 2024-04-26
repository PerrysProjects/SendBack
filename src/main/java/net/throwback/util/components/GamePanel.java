package net.throwback.util.components;

import net.throwback.objects.GameObject;
import net.throwback.objects.TileObject;
import net.throwback.objects.WorldObject;
import net.throwback.objects.entity.MovementType;
import net.throwback.objects.entity.Player;
import net.throwback.objects.objectId.ObjectId;
import net.throwback.util.Resources;
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
    private int currentFps;
    private long lastFpsCheckTime;
    private int frameCount;

    private final int zoom;

    private int cameraX, cameraY;
    private int maxTop, maxBottom, maxLeft, maxRight;

    private Session session;
    private World world;
    private Player player;

    private GamePanel() {
        thread = new Thread(this);
        fps = 60;
        currentFps = 0;
        lastFpsCheckTime = System.nanoTime();
        frameCount = 0;

        zoom = 80;

        cameraX = -1;
        cameraY = -1;

        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addComponentListener(this);
        setLayout(null);
    }

    public static GamePanel getInstance() {
        if(instance == null) {
            instance = new GamePanel();
        }
        return instance;
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

        //setFocusable(true);
        //requestFocus();
        //revalidate();
        repaint();
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

    private void calculateFPS() {
        long currentTime = System.nanoTime();
        if (currentTime - lastFpsCheckTime >= 1_000_000_000) {
            currentFps = frameCount;
            frameCount = 0;
            lastFpsCheckTime = currentTime;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(Resources.getFonts()[0]);

        TileObject[][] tileGrid = world.getTileGrid();
        tileGrid[80][80] = new TileObject(80, 80, ObjectId.EXAMPLE);
        tileGrid[80][0] = new TileObject(80, 0, ObjectId.EXAMPLE);

        WorldObject[][] worldGrid = world.getWorldGrid();

        int startX = (int) player.getX() - (getWidth() / (2 * zoom));
        int endX = (int) player.getX() + (getWidth() / (2 * zoom)) + 1;
        int startY = (int) player.getY() - (getHeight() / (2 * zoom));
        int endY = (int) player.getY() + (getHeight() / (2 * zoom)) + 1;

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

        for(int x = startX - 1; x <= endX + 1; x++) {
            for(int y = startY - 1; y <= endY + 1; y++) {
                int screenX = (int) ((x - startX) * zoom - Math.ceil((player.getX() % 1) * zoom)) + extraX;
                int screenY = (int) ((y - startY) * zoom - Math.ceil((player.getY() % 1) * zoom)) + extraY;

                TileObject tileObject = world.getBorderTile();
                if(x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight()) {
                    tileObject = tileGrid[x][y];
                }

                int width = (int) ((double) tileObject.getWidth() / GameObject.getStandardWidth() * zoom);
                int height = (int) ((double) tileObject.getHeight() / GameObject.getStandardHeight() * zoom);
                g2.drawImage(tileObject.getTextures()[0], screenX - (width - zoom), screenY - (height - zoom),
                        width, height, this);
            }
        }

        int ovalX = getWidth() / 2 - zoom / 2;
        int ovalY = getHeight() / 2 - zoom / 2;
        g2.setColor(Color.RED);
        g2.drawRect(ovalX, ovalY, zoom, zoom);

        for(int x = startX - 1; x <= endX + 1; x++) {
            for(int y = startY - 1; y <= endY + 1; y++) {
                int screenX = (int) ((x - startX) * zoom - Math.ceil((player.getX() % 1) * zoom)) + extraX;
                int screenY = (int) ((y - startY) * zoom - Math.ceil((player.getY() % 1) * zoom)) + extraY;

                WorldObject worldObject = null;
                if(x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight()) {
                    if(worldGrid[x][y] != null) {
                        worldObject = worldGrid[x][y];
                    }
                }

                if(worldObject != null) {
                    int width = (int) ((double) worldObject.getWidth() / GameObject.getStandardWidth() * zoom);
                    int height = (int) ((double) worldObject.getHeight() / GameObject.getStandardHeight() * zoom);
                    g2.drawImage(worldObject.getTextures()[0], screenX - (width - zoom), screenY - (height - zoom),
                            width, height, this);
                }
            }
        }

        g2.setColor(Color.YELLOW);
        g2.drawString("FPS: " + currentFps, 50, 50);
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while(thread.isAlive()) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                delta--;
                frameCount++;
            }

            calculateFPS();

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
