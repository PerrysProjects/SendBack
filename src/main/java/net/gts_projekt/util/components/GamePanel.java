package net.gts_projekt.util.components;

import net.gts_projekt.Main;
import net.gts_projekt.objects.entity.MoveType;
import net.gts_projekt.objects.entity.Player;
import net.gts_projekt.util.Session;
import net.gts_projekt.worlds.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener, ComponentListener {
    private Thread thread;
    private int fps;
    private int cf;

    private int zoom;

    private int cameraX, cameraY;
    private int maxTop, maxBottom, maxLeft, maxRight;

    private Session session;
    private World world;
    private Player player;

    public GamePanel() {
        thread = new Thread(this);
        fps = 60;
        cf = 1;

        zoom = 40;

        cameraX = -1;
        cameraY = -1;

        /*session = Main.getCurrentSession();
        world = session.getWorlds()[0];
        player = session.getPlayer();*/

        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addComponentListener(this);
        setLayout(null);

        start();
    }

    private void update() {
        if(Main.getFrame() != null) {
            if(cameraX == -1 && cameraY == -1) {
                cameraX = Main.getFrame().getWidth() / 2;
                cameraY = Main.getFrame().getHeight() / 2;
            }

            maxTop = Main.getFrame().getHeight() / 3;
            maxBottom = Main.getFrame().getHeight() - maxTop;
            maxLeft = Main.getFrame().getWidth() / 3;
            maxRight = Main.getFrame().getWidth() - maxLeft;
        }

        if(Main.getCurrentSession() != null && session == null) {
            session = Main.getCurrentSession();
            world = session.getWorlds()[0];
            player = session.getPlayer();
        }

        setFocusable(true);
        requestFocus();
        repaint();
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(session != null) {
            double[][] grid = world.getGrid();
            grid[80][80] = 60;
            grid[80][0] = 60;

            int startX = (int) player.getX() - (getWidth() / (2 * zoom));
            int endX = (int) player.getX() + (getWidth() / (2 * zoom)) + 1;
            int startY = (int) player.getY() - (getHeight() / (2 * zoom));
            int endY = (int) player.getY() + (getHeight() / (2 * zoom)) + 1;

            for(int x = startX - 1; x <= endX + 1; x++) {
                for(int y = startY - 1; y <= endY + 1; y++) {
                    //System.out.println((getWidth() % zoom) / 2);
                    //System.out.println((getWidth() / 2 - zoom / 2) % zoom);

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

                    int rgb = Color.cyan.getRGB();
                    if(x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight()) {
                        double value = grid[x][y];
                        rgb = (value < 0.2 && value > -0.2) ? 0 : 0x010101 * 255;
                        rgb = (value == 60) ? Color.blue.getRGB() : rgb;
                    }

                    g2.setColor(new Color(rgb));
                    g2.fillRect(screenX, screenY, zoom, zoom);
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
            case KeyEvent.VK_W -> player.startMoving(MoveType.UP);
            case KeyEvent.VK_A -> player.startMoving(MoveType.LEFT);
            case KeyEvent.VK_S -> player.startMoving(MoveType.DOWN);
            case KeyEvent.VK_D -> player.startMoving(MoveType.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> player.stopMoving(MoveType.UP);
            case KeyEvent.VK_A -> player.stopMoving(MoveType.LEFT);
            case KeyEvent.VK_S -> player.stopMoving(MoveType.DOWN);
            case KeyEvent.VK_D -> player.stopMoving(MoveType.RIGHT);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(Main.getFrame() != null) {
            cameraX = Main.getFrame().getWidth() / 2;
            cameraY = Main.getFrame().getHeight() / 2;
        }
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
