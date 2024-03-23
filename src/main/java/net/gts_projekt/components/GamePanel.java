package net.gts_projekt.components;

import net.gts_projekt.util.procedural.PerlinNoise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread thread;
    private int fps;

    public GamePanel() {
        thread = new Thread(this);
        fps = 60;

        addKeyListener(this);
        setLayout(null);

        start();
    }

    private void update() {

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

        for(int i = 0; i < this.getWidth()/16; i++) {
            for(int j = 0; j < this.getHeight()/16; j++) {
                double value = PerlinNoise.noise(i, j, 0.0, 20, 798887); //OpenSimplex2S.noise3_ImproveXY(798887, i * 0.05, j * 0.05, 0.0);
                int rgb; //= 0x010101 * (int)((value + 1) * 127.5);
                if(value < 0.2 && value > -0.2)
                    rgb = 0;
                else
                    rgb = 0x010101 * 255;

                System.out.println(value);

                g2.setColor(new Color(rgb));
                g2.fillRect(i*16, j*16, 16, 16);
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/fps;
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
