package net.gts_projekt.components;

import net.gts_projekt.Main;
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

    private int zoom;

    private double currentX, currentY;

    private int cameraX, cameraY;
    private int maxTop, maxBottom, maxLeft, maxRight;

    public GamePanel() {
        thread = new Thread(this);
        fps = 60;

        zoom = 80;

        currentX = 80;
        currentY = 80;

        cameraX = -1;
        cameraY = -1;

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

        if(Main.getCurrentSession() != null) {
            World world = Main.getCurrentSession().getWorlds()[0];
            double[][] grid = world.getGrid();
            grid[80][80] = 60;

            int startX = (int) Math.max(0, currentX - (getWidth() / (2 * zoom)));
            int endX = (int) Math.min(world.getWidth(), currentX + (getWidth() / (2 * zoom)) + 1);
            int startY = (int) Math.max(0, currentY - (getHeight() / (2 * zoom)));
            int endY = (int) Math.min(world.getHeight(), currentY + (getHeight() / (2 * zoom)) + 1);

            //System.out.println(startX + " " + endX);
            System.out.println((startX + endX) / 2);
            System.out.println((startY + endY) / 2);
            //System.out.println(startY + " " + endY);

            for(int x = startX; x <= endX + 1; x++) {
                for(int y = startY; y <= endY + 1; y++) {
                    int screenX = (int) ((x - startX) * zoom - Math.ceil(currentX % 1 * zoom));
                    int screenY = (int) ((y - startY) * zoom - Math.ceil(currentY % 1 * zoom));

                    if(x == 80 && y == 80) {
                        System.out.println(screenX + "xxxx" + screenY);
                    }

                    double value = grid[x][y];
                    int rgb = (value < 0.2 && value > -0.2) ? 0 : 0x010101 * 255;
                    rgb = (value == 60) ? Color.blue.getRGB() : rgb;

                    g2.setColor(new Color(rgb));
                    g2.fillRect(screenX, screenY, zoom, zoom);
                }
            }
        }

        g2.setColor(Color.red);
        g2.fillRect(0, 0, 16, 16);
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
            case KeyEvent.VK_W -> currentY -= 0.1;
            case KeyEvent.VK_A -> currentX -= 0.1;
            case KeyEvent.VK_S -> currentY += 0.1;
            case KeyEvent.VK_D -> currentX += 0.1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

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
