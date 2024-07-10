package net.sendback.util.components;

import net.sendback.objects.TileObject;
import net.sendback.objects.WorldObject;
import net.sendback.objects.entity.MovementType;
import net.sendback.objects.entity.Player;
import net.sendback.util.Resources;
import net.sendback.util.Session;
import net.sendback.worlds.World;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas implements Runnable, KeyListener, ComponentListener {
    private static GameCanvas instance;

    private final Thread thread;
    private final int fps;
    private int currentFps;
    private long lastFpsCheckTime;
    private int frameCount;

    private final int zoom;

    private Session session;
    private World world;
    private Player player;

    private BufferStrategy bufferStrategy;

    private GameCanvas() {
        thread = new Thread(this);
        fps = 60;
        currentFps = 0;
        lastFpsCheckTime = System.currentTimeMillis();
        frameCount = 0;

        zoom = 14;

        addKeyListener(this);
        addComponentListener(this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();
    }

    public static GameCanvas getInstance() {
        if(instance == null) {
            instance = new GameCanvas();
        }

        return instance;
    }

    private void update() {
        frameCount++;

        render();
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

    private void render() {
        if(bufferStrategy != null) {
            Graphics2D g2 = (Graphics2D) bufferStrategy.getDrawGraphics();

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

                    if(tilePosX == 80 && tilePosY == 80) {
                        g2.setColor(Color.CYAN);
                        g2.fillRect(screenPosX, screenPosY, tileSize, tileSize);
                    } else {
                        g2.drawImage(tileObject.getTextures()[0], screenPosX + tileOffsetX, screenPosY + tileOffsetY,
                                tileWidth, tileHeight, this);
                    }

                    if(tilePosX >= 0 && tilePosX < world.getTileGrid().length &&
                            tilePosY >= 0 && tilePosY < world.getTileGrid()[0].length) {
                        WorldObject worldObject = world.getWorldGrid()[tilePosX][tilePosY];

                        if(worldObject != null) {
                            g2.drawImage(worldObject.getTextures()[0], screenPosX + tileOffsetX, screenPosY + tileOffsetY,
                                    tileWidth, tileHeight, this);
                        }
                    }
                }
            }

            int ovalX = getWidth() / 2 - tileSize / 2;
            int ovalY = getHeight() / 2 - tileSize / 2;
            g2.setColor(Color.RED);
            g2.drawRect(ovalX, ovalY, tileSize, tileSize);

            /*for(int x = 0; x < tileScreenWidth + 2; x++) {
                for(int y = 0; y < tileScreenHeight + 2; y++) {
                    int tilePosX = (int) (player.getX() - tileScreenWidth / 2 + x);
                    int tilePosY = (int) (player.getY() - tileScreenHeight / 2 + y);

                    if(tilePosX >= 0 && tilePosX < world.getTileGrid().length &&
                            tilePosY >= 0 && tilePosY < world.getTileGrid()[0].length) {
                        WorldObject worldObject = world.getWorldGrid()[tilePosX][tilePosY];

                        if(worldObject != null) {
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

                            int tileWidth = (int) (tileSize * worldObject.getWidth());
                            int tileHeight = (int) (tileSize * worldObject.getHeight());

                            int tileOffsetX = tileSize - tileWidth;
                            int tileOffsetY = tileSize - tileHeight;

                            g2.drawImage(worldObject.getTextures()[0], screenPosX + tileOffsetX, screenPosY + tileOffsetY,
                                    tileWidth, tileHeight, this);
                        }
                    }
                }
            }*/

            g2.setColor(Color.YELLOW);
            g2.drawString("FPS: " + currentFps, 50, 50);

            g2.dispose();
            bufferStrategy.show();
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000 / fps;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;

        while(thread.isAlive()) {
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                delta--;
            }

            calculateFPS();

            while(System.currentTimeMillis() - lastTime < drawInterval) {
                Thread.yield();
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
