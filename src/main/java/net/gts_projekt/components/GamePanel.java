package net.gts_projekt.components;

import net.gts_projekt.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread thread;
    private int fps;

    private int zoom;
    private int lastWindowWidth;
    private int lastWindowHeight;

    private int cameraX, cameraY;
    private int maxTop, maxBottom, maxLeft, maxRight;

    public GamePanel() {
        thread = new Thread(this);
        fps = 60;

        zoom = 16;

        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        setLayout(null);

        start();
    }

    private void update() {
        if(Main.getFrame() != null) {
            cameraX = Main.getFrame().getWidth() / 2;
            cameraY = Main.getFrame().getHeight() / 2;

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
            int width = Main.getCurrentSession().getWorlds()[0].getWidth();
            int height = Main.getCurrentSession().getWorlds()[0].getHeight();

            for(int x = 0; x < width; x++) {
                for(int y = 0; y < height; y++) {
                    double value = Main.getCurrentSession().getWorlds()[0].getGrid()[x][y];
                    int rgb = (value < 0.2 && value > -0.2) ? 0 : 0x010101 * 255;

                    g2.setColor(new Color(rgb));
                    g2.fillRect(x * zoom, y * zoom, zoom, zoom);
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
}
