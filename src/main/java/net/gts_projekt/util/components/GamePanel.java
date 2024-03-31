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

        zoom = 80;

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

            int startX = (int) Math.max(0, player.getX() - (getWidth() / (2 * zoom)));
            int endX = (int) Math.min(world.getWidth(), player.getX() + (getWidth() / (2 * zoom)) + 1);
            int startY = (int) Math.max(0, player.getY() - (getHeight() / (2 * zoom)));
            int endY = (int) Math.min(world.getHeight(), player.getY() + (getHeight() / (2 * zoom)) + 1);

            System.out.println(startY);

            for(int x = startX; x <= endX + 1; x++) {
                for(int y = startY; y <= endY + 1; y++) {
                    int screenX = (int) ((x - startX) * zoom - Math.ceil((player.getX() % 1) * zoom));
                    int screenY = (int) ((y - startY) * zoom - Math.ceil((player.getY() % 1) * zoom));

                    if(x == 80 && y == 80) {
                        //System.out.println(screenX + "xxxx" + screenY);
                        //System.out.println(player.getX() + "xxxxxxx" + player.getY());
                    }

                    double value = grid[x][y];
                    int rgb = (value < 0.2 && value > -0.2) ? 0 : 0x010101 * 255;
                    rgb = (value == 60) ? Color.blue.getRGB() : rgb;

                    g2.setColor(new Color(rgb));
                    g2.fillRect(screenX, screenY, zoom, zoom);
                }
            }
            g2.setColor(Color.red);
            g2.fillOval(cameraX - zoom / 2, cameraY - zoom / 2, zoom, zoom);
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
                if(cf != 60) {
                    cf++;
                } else {
                    cf = 1;
                }
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
            case KeyEvent.VK_W -> {
                if(player.getY() > 0) {
                    player.startMoving(MoveType.UP);
                } else {
                    player.stopMoving(MoveType.UP);
                }
            }
            case KeyEvent.VK_A -> {
                if(player.getX() > 0) {
                    player.startMoving(MoveType.LEFT);
                } else {
                    player.stopMoving(MoveType.LEFT);
                }
            }
            case KeyEvent.VK_S -> {
                if(player.getY() < world.getHeight()) {
                    player.startMoving(MoveType.DOWN);
                } else {
                    player.stopMoving(MoveType.DOWN);
                }
            }
            case KeyEvent.VK_D -> {
                if(player.getX() < world.getWidth()) {
                    player.startMoving(MoveType.RIGHT);
                } else {
                    player.stopMoving(MoveType.RIGHT);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                player.stopMoving(MoveType.UP);
            }
            case KeyEvent.VK_A -> {
                player.stopMoving(MoveType.LEFT);
            }
            case KeyEvent.VK_S -> {
                player.stopMoving(MoveType.DOWN);
            }
            case KeyEvent.VK_D -> {
                player.stopMoving(MoveType.RIGHT);
            }
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
