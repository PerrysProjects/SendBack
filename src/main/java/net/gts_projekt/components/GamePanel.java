package net.gts_projekt.components;

import net.gts_projekt.Main;

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

    double currentX, currentY;

    private int cameraX, cameraY;
    private int maxTop, maxBottom, maxLeft, maxRight;

    public GamePanel() {
        thread = new Thread(this);
        fps = 60;

        zoom = 16;

        currentX = 8;
        currentY = 8;

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
            int width = Main.getFrame().getWidth();
            int height = Main.getFrame().getHeight();

            for(int x = -1; x <= width / zoom; x++) {
                for(int y = -1; y <= height / zoom; y++) {
                    int screenX = (int) (currentX - width / zoom / 2 + x);
                    int screenY = (int) (currentY - height / zoom / 2 + y);

                    int rgb = Color.blue.getRGB();

                    if(screenX >= 0 && screenX < Main.getCurrentSession().getWorlds()[0].getWidth()
                            && screenY >= 0 && screenY < Main.getCurrentSession().getWorlds()[0].getHeight()) {
                        double value = Main.getCurrentSession().getWorlds()[0].getGrid()[screenX][screenY];
                        rgb = (value < 0.2 && value > -0.2) ? 0 : 0x010101 * 255;
                    }


                    g2.setColor(new Color(rgb));
                    g2.fillRect(x * zoom + cameraX % zoom, y * zoom + cameraY % zoom, zoom, zoom);

                    g2.setColor(Color.red);
                    g2.drawString(String.valueOf(screenX), x * zoom + cameraX % zoom, y * zoom + cameraY % zoom);
                }
            }

            int beanSize = 16;
            g2.setColor(Color.red);
            g2.fillOval(cameraX - beanSize / 2, cameraY - beanSize / 2, beanSize, beanSize);

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
