package net.sendback.util.components;

import net.sendback.objects.FloorTile;
import net.sendback.objects.WorldTile;
import net.sendback.objects.entity.MovementType;
import net.sendback.objects.entity.Player;
import net.sendback.util.resources.ResourceGetter;
import net.sendback.util.Session;
import net.sendback.worlds.World;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

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

            g2.setFont(ResourceGetter.getFonts()[0]);

            int tileSize = getWidth() / zoom;

            int tileScreenWidth = zoom;
            int tileScreenHeight = getHeight() / tileSize;

            for(int x = 0; x < tileScreenWidth + 2; x++) {
                for(int y = 0; y < tileScreenHeight + 2; y++) {
                    int tilePosX = (int) (player.getX() - tileScreenWidth / 2 + x);
                    int tilePosY = (int) (player.getY() - tileScreenHeight / 2 + y);

                    FloorTile floorTile = world.getBorderTile();
                    if(tilePosX >= 0 && tilePosX < world.getWidth() &&
                            tilePosY >= 0 && tilePosY < world.getHeight()) {
                        floorTile = world.getTileGrid()[tilePosX][tilePosY];
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

                    int tileWidth = (int) (tileSize * floorTile.getWidth());
                    int tileHeight = (int) (tileSize * floorTile.getHeight());

                    int tileOffsetX = tileSize - tileWidth;
                    int tileOffsetY = tileSize - tileHeight;

                    if(tilePosX == 80 && tilePosY == 80) {
                        g2.setColor(Color.CYAN);
                        g2.fillRect(screenPosX, screenPosY, tileSize, tileSize);
                    } else {
                        g2.drawImage(floorTile.getTextures().getTexture(), screenPosX + tileOffsetX, screenPosY + tileOffsetY,
                                tileWidth, tileHeight, this);
                    }

                    if(tilePosX >= 0 && tilePosX < world.getWidth() &&
                            tilePosY >= 0 && tilePosY < world.getHeight()) {
                        WorldTile worldTile = world.getWorldGrid()[tilePosX][tilePosY];

                        if(worldTile != null) {
                            BufferedImage texture = worldTile.getTextures().getTexture();
                            if(tilePosX > 0 && tilePosX < world.getWidth() - 1 &&
                                    tilePosY > 0 && tilePosY < world.getHeight() - 1) {
                                if(world.getWorldGrid()[tilePosX - 1][tilePosY] == null &&
                                        world.getWorldGrid()[tilePosX - 1][tilePosY - 1] == null &&
                                        world.getWorldGrid()[tilePosX][tilePosY - 1] == null) {
                                    texture = ResourceGetter.getTileTexture("tree_corner_up_left.png");
                                } else if(world.getWorldGrid()[tilePosX - 1][tilePosY] != null &&
                                        world.getWorldGrid()[tilePosX][tilePosY - 1] == null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_up.png");
                                } else if(world.getWorldGrid()[tilePosX + 1][tilePosY] == null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY - 1] == null &&
                                        world.getWorldGrid()[tilePosX][tilePosY - 1] == null) {
                                    texture = ResourceGetter.getTileTexture("tree_corner_up_right.png");
                                } else if(world.getWorldGrid()[tilePosX][tilePosY - 1] != null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY] == null &&
                                        world.getWorldGrid()[tilePosX][tilePosY + 1] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_right.png");
                                } else if(world.getWorldGrid()[tilePosX + 1][tilePosY] == null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY + 1] == null &&
                                        world.getWorldGrid()[tilePosX][tilePosY + 1] == null) {
                                    texture = ResourceGetter.getTileTexture("tree_corner_down_right.png");
                                } else if(world.getWorldGrid()[tilePosX - 1][tilePosY] != null &&
                                        world.getWorldGrid()[tilePosX][tilePosY + 1] == null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_down.png");
                                } else if(world.getWorldGrid()[tilePosX - 1][tilePosY] == null &&
                                        world.getWorldGrid()[tilePosX - 1][tilePosY + 1] == null &&
                                        world.getWorldGrid()[tilePosX][tilePosY + 1] == null) {
                                    texture = ResourceGetter.getTileTexture("tree_corner_down_left.png");
                                } else if(world.getWorldGrid()[tilePosX][tilePosY - 1] != null &&
                                        world.getWorldGrid()[tilePosX - 1][tilePosY] == null &&
                                        world.getWorldGrid()[tilePosX][tilePosY + 1] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_left.png");
                                } else if(world.getWorldGrid()[tilePosX][tilePosY - 1] != null &&
                                        world.getWorldGrid()[tilePosX - 1][tilePosY - 1] == null &&
                                        world.getWorldGrid()[tilePosX - 1][tilePosY] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_inverted_down_right.png");
                                } else if(world.getWorldGrid()[tilePosX][tilePosY - 1] != null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY - 1] == null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_inverted_down_left.png");
                                } else if(world.getWorldGrid()[tilePosX][tilePosY + 1] != null &&
                                        world.getWorldGrid()[tilePosX - 1][tilePosY + 1] == null &&
                                        world.getWorldGrid()[tilePosX - 1][tilePosY] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_inverted_up_right.png");
                                } else if(world.getWorldGrid()[tilePosX][tilePosY + 1] != null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY + 1] == null &&
                                        world.getWorldGrid()[tilePosX + 1][tilePosY] != null) {
                                    texture = ResourceGetter.getTileTexture("tree_inverted_up_left.png");
                                }
                            }

                            g2.drawImage(texture, screenPosX + tileOffsetX, screenPosY + tileOffsetY,
                                    tileWidth, tileHeight, this);
                        }
                    }
                }
            }

            int playerX = getWidth() / 2 - tileSize / 2;
            int playerY = getHeight() / 2 - tileSize / 2;
            //g2.setColor(Color.RED);
            //g2.drawRect(ovalX, ovalY, tileSize, tileSize);
            g2.drawImage(ResourceGetter.getEntityTexture("prof_noGlasses.png"), playerX, playerY, tileSize, tileSize, this);

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
