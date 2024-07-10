package net.sendback.util.components;

import net.sendback.objects.TileObject;
import net.sendback.objects.entity.MovementType;
import net.sendback.objects.entity.Player;
import net.sendback.util.Resources;
import net.sendback.util.Session;
import net.sendback.worlds.World;

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

    private Session session;
    private World world;
    private Player player;

    private GamePanel() {
        thread = new Thread(this);
        fps = 60;
        currentFps = 0;
        lastFpsCheckTime = System.currentTimeMillis();
        frameCount = 0;

        zoom = 14;

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

        frameCount++;

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
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastFpsCheckTime >= 1000) {
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

        int tileSize = getWidth() / zoom;

        int tileScreenWidth = zoom;
        int tileScreenHeight = getHeight() / tileSize;

        for(int x = 0; x < tileScreenWidth + 2; x++) {
            for(int y = 0; y < tileScreenHeight + 2; y++) {
                int tilePosX = (int) (player.getX() - tileScreenWidth / 2 + x);
                int tilePosY = (int) (player.getY() - tileScreenHeight / 2 + y);

                TileObject tileObject = world.getBorderTile();
                if(tilePosX >= 0 && tilePosX < world.getTileGrid().length &&
                        tilePosY >= 0 && tilePosY < world.getTileGrid()[0].length) {
                    tileObject = world.getTileGrid()[tilePosX][tilePosY];
                }

                double offsetX = player.getX() - (int) player.getX();
                double offsetY = player.getY() - (int) player.getY();

                int extraX = (getWidth() / 2 - tileSize / 2) % tileSize;
                if(extraX >= tileSize / 2) {
                    extraX -= tileSize;
                }

                int extraY = (getHeight() / 2 - tileSize / 2) % tileSize;
                if(extraY >= tileSize / 2) {
                    extraY -= tileSize;
                }

                int screenPosX = (int) (tileSize * x - tileSize * offsetX + extraX);
                int screenPosY = (int) (tileSize * y - tileSize * offsetY + extraY);

                int tileWidth = (int) (tileSize * tileObject.getWidth());
                int tileHeight = (int) (tileSize * tileObject.getHeight());

                int tileOffsetX = tileSize - tileWidth;
                int tileOffsetY = tileSize - tileHeight;

                if(tilePosX == 5 && tilePosY == 5) {
                    g2.setColor(Color.CYAN);
                    g2.fillRect(screenPosX, screenPosY, tileSize, tileSize);
                } else {
                    g2.drawImage(tileObject.getTextures()[0], screenPosX + tileOffsetX, screenPosY + tileOffsetY,
                            tileWidth, tileHeight, this);
                }
            }
        }

        int ovalX = getWidth() / 2 - tileSize / 2;
        int ovalY = getHeight() / 2 - tileSize / 2;
        g2.setColor(Color.RED);
        g2.drawRect(ovalX, ovalY, tileSize, tileSize);

        g2.setColor(Color.YELLOW);
        g2.drawString("FPS: " + currentFps, 50, 50);

        g2.dispose();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000 / fps;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        long timer = 0;

        while(thread.isAlive()) {
            currentTime = System.currentTimeMillis();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                delta--;
            }

            calculateFPS();

            if(timer >= 1000) {
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
