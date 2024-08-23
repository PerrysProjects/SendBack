package net.sendback.util.components;


import net.sendback.objects.Tile;
import net.sendback.objects.entity.MovementType;
import net.sendback.objects.entity.Player;
import net.sendback.util.Session;
import net.sendback.util.Settings;

import net.sendback.util.resources.ResourceGetter;
import net.sendback.worlds.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameCanvas extends Canvas implements Runnable, KeyListener {
    private static GameCanvas instance;

    private final Thread thread;
    private final int fps;
    private int currentFps;
    private long lastFpsCheckTime;
    private int frameCount;

    private Session session;
    private World world;
    private Player player;

    public int zoom = 80; //
    private double centerX;
    private double centerY;
    private int maxTop, maxBottom, maxLeft, maxRight;

    private int resetTimer;

    private BufferStrategy bufferStrategy;

    private boolean paused;
    private final Object lock = new Object();

    private GameCanvas() {
        thread = new Thread(this);
        fps = 60;
        currentFps = 0;
        lastFpsCheckTime = System.currentTimeMillis();
        frameCount = 0;

        zoom = 80;

        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        addKeyListener(this);
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

        maxTop = getHeight() / 3;
        maxBottom = getHeight() - maxTop;
        maxLeft = getWidth() / 3;
        maxRight = getWidth() - maxLeft;

        repaint();

        render();
    }

    public void start() {
        if(session != null) {
            if(paused) {
                resume();
            } else if(!thread.isAlive()) {
                thread.start();
                session.start();
            }
        }
    }

    public void stop() {
        thread.interrupt();
        session.stop();
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized(lock) {
            paused = false;
            lock.notify();
        }
    }

    public void setup(Session session) {
        if(thread.isAlive()) {
            stop();
        }
        this.session = session;
        world = session.getWorlds()[0];
        player = session.getPlayer();

        centerX = player.getX();
        centerY = player.getY();

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

    private void drawTile(Graphics2D g2, Tile[][] tileGrid, Tile borderTile, int tx, int ty, int x, int y, int tileSize) {
        if(tx >= 0 && tx < tileGrid.length && ty >= 0 && ty < tileGrid[0].length) {
            Tile tile = tileGrid[tx][ty];

            if(tile != null) {
                BufferedImage texture = tile.getTextures().getTexture();
                if(tile.getTextures().getLeftSideTexture() != null) {
                    if(tx > 0 && tx < tileGrid.length - 1 && ty > 0 && ty < tileGrid[0].length - 1) {
                        if(tileGrid[tx - 1][ty] == null &&
                                tileGrid[tx - 1][ty - 1] == null &&
                                tileGrid[tx][ty - 1] == null) {
                            texture = tile.getTextures().getTopLeftCornerTexture();
                        } else if(tileGrid[tx - 1][ty] != null &&
                                tileGrid[tx][ty - 1] == null &&
                                tileGrid[tx + 1][ty] != null) {
                            texture = tile.getTextures().getTopSideTexture();
                        } else if(tileGrid[tx + 1][ty] == null &&
                                tileGrid[tx + 1][ty - 1] == null &&
                                tileGrid[tx][ty - 1] == null) {
                            texture = tile.getTextures().getTopRightCornerTexture();
                        } else if(tileGrid[tx][ty - 1] != null &&
                                tileGrid[tx + 1][ty] == null &&
                                tileGrid[tx][ty + 1] != null) {
                            texture = tile.getTextures().getRightSideTexture();
                        } else if(tileGrid[tx + 1][ty] == null &&
                                tileGrid[tx + 1][ty + 1] == null &&
                                tileGrid[tx][ty + 1] == null) {
                            texture = tile.getTextures().getBottomRightCornerTexture();
                        } else if(tileGrid[tx - 1][ty] != null &&
                                tileGrid[tx][ty + 1] == null &&
                                tileGrid[tx + 1][ty] != null) {
                            texture = tile.getTextures().getBottomSideTexture();
                        } else if(tileGrid[tx - 1][ty] == null &&
                                tileGrid[tx - 1][ty + 1] == null &&
                                tileGrid[tx][ty + 1] == null) {
                            texture = tile.getTextures().getBottomLeftCornerTexture();
                        } else if(tileGrid[tx][ty - 1] != null &&
                                tileGrid[tx - 1][ty] == null &&
                                tileGrid[tx][ty + 1] != null) {
                            texture = tile.getTextures().getLeftSideTexture();
                        } else if(tileGrid[tx][ty - 1] != null &&
                                tileGrid[tx + 1][ty - 1] == null &&
                                tileGrid[tx + 1][ty] != null) {
                            texture = tile.getTextures().getInvertedBottomLeftCornerTexture();
                        } else if(tileGrid[tx][ty - 1] != null &&
                                tileGrid[tx - 1][ty - 1] == null &&
                                tileGrid[tx - 1][ty] != null) {
                            texture = tile.getTextures().getInvertedBottomRightCornerTexture();
                        } else if(tileGrid[tx][ty + 1] != null &&
                                tileGrid[tx + 1][ty + 1] == null &&
                                tileGrid[tx + 1][ty] != null) {
                            texture = tile.getTextures().getInvertedTopLeftCornerTexture();
                        } else if(tileGrid[tx][ty + 1] != null &&
                                tileGrid[tx - 1][ty + 1] == null &&
                                tileGrid[tx - 1][ty] != null) {
                            texture = tile.getTextures().getInvertedTopRightCornerTexture();
                        } else {
                            texture = tile.getTextures().getTexture();
                        }

                    }
                }

                int tileWidth = (int) (tileSize * tile.getWidth());
                int tileHeight = (int) (tileSize * tile.getHeight());

                int tileOffsetX = tileSize - tileWidth;
                int tileOffsetY = tileSize - tileHeight;

                g2.drawImage(texture, x + tileOffsetX, y + tileOffsetY, tileWidth, tileHeight, this);
            }
        } else {
            if(borderTile != null) {
                BufferedImage texture = borderTile.getTextures().getTexture();

                int tileWidth = (int) (tileSize * borderTile.getWidth());
                int tileHeight = (int) (tileSize * borderTile.getHeight());

                int tileOffsetX = tileSize - tileWidth;
                int tileOffsetY = tileSize - tileHeight;

                g2.drawImage(texture, x + tileOffsetX, y + tileOffsetY, tileWidth, tileHeight, this);
            }
        }
    }

    private void render() {
        if(bufferStrategy != null) {
            Graphics2D g2 = (Graphics2D) bufferStrategy.getDrawGraphics();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);


            g2.setFont(ResourceGetter.getFonts()[0]);

            int zoom = (int) Settings.getSetting("zoom");

            int tileSize = 0;
            while(tileSize == 0) {
                tileSize = getWidth() / zoom;
            }

            int tileScreenWidth = zoom;
            int tileScreenHeight = getHeight() / tileSize;

            double offsetX = player.getX() - (int) player.getX();
            double offsetY = player.getY() - (int) player.getY();

            for(int x = 0; x < tileScreenWidth + 2; x++) {
                for(int y = 0; y < tileScreenHeight + 2; y++) {
                    int tilePosX = (int) (centerX - tileScreenWidth / 2 + x);
                    int tilePosY = (int) (centerY - tileScreenHeight / 2 + y);

                    int extraX = (getWidth() / 2 - tileSize / 2) % tileSize;
                    if(extraX >= tileSize / 2) {
                        extraX -= tileSize;
                    }

                    int extraY = (getHeight() / 2 - tileSize / 2) % tileSize;
                    if(extraY >= tileSize / 2) {
                        extraY -= tileSize;
                    }

                    int screenPosX = tileSize * x + extraX;
                    int screenPosY = tileSize * y + extraY;

                    /*int tilePosX = (int) (player.getX() - tileScreenWidth / 2 + x);
                    int tilePosY = (int) (player.getY() - tileScreenHeight / 2 + y);

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
                    int screenPosY = (int) (tileSize * y - tileSize * offsetY + extraY);*/

                    //drawTile(g2, world.getTileGrid(), world.getBorderTile(), tilePosX, tilePosY, screenPosX, screenPosY, tileSize);
                    //drawTile(g2, world.getWorldGrid(), world.getBorderWorldTile(), tilePosX, tilePosY, screenPosX, screenPosY, tileSize);

                    g2.drawImage(world.getTileGrid()[tilePosX][tilePosY].getTextures().getTexture(), screenPosX, screenPosY, tileSize, tileSize, this);
                    if(world.getWorldGrid()[tilePosX][tilePosY] != null) {
                        g2.drawImage(world.getWorldGrid()[tilePosX][tilePosY].getTextures().getTexture(), screenPosX, screenPosY, tileSize, tileSize, this);
                    }

                    if(tilePosX == 80 && tilePosY == 80 || tilePosX == 82 && tilePosY == 80 || tilePosX == 84 && tilePosY == 80 || tilePosX == 86 && tilePosY == 80) {
                        g2.setColor(Color.CYAN);
                        g2.fillRect(screenPosX, screenPosY, tileSize, tileSize);
                    }
                }
            }

            //int playerX = (int) (getWidth() / 2 - tileSize / 2 + (int) (player.getX() - centerX) * tileSize + tileSize * offsetX);

            if(!player.isMoving(MovementType.UP) && !player.isMoving(MovementType.LEFT) &&
                    !player.isMoving(MovementType.DOWN) && !player.isMoving(MovementType.RIGHT)) {
                resetTimer++;
                if(resetTimer >= fps * 3) {
                    centerX = player.getX();
                    centerY = player.getY();
                }
            } else {
                resetTimer = 0;
            }

            int centerOffsetX = (int) Math.floor(player.getX() - centerX);
            int centerOffsetY = (int) Math.floor(player.getY() - centerY);

            if(centerOffsetX > 3) {
                centerX++;
                centerOffsetX = 3;
            } else if(centerOffsetX < -3) {
                centerX--;
                centerOffsetX = 3;
            }

            if(centerOffsetY > 3) {
                centerY++;
                centerOffsetY = 3;
            } else if(centerOffsetY < -3) {
                centerY--;
                centerOffsetY = 3;
            }

            int playerX = (int) (getWidth() / 2 - tileSize / 2 + centerOffsetX * tileSize + offsetX * tileSize);
            System.out.println(player.isMoving(MovementType.DOWN));
            int playerY = (int) (getHeight() / 2 - tileSize / 2 + centerOffsetY * tileSize + offsetY * tileSize);
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

        while(!Thread.currentThread().isInterrupted()) {
            synchronized(lock) {
                while(paused) {
                    try {
                        lock.wait();
                    } catch(InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

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
        char key = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase().charAt(0);

        char forward = (char) Settings.getSetting("forward");
        char left = (char) Settings.getSetting("left");
        char backward = (char) Settings.getSetting("backward");
        char right = (char) Settings.getSetting("right");

        if(key == forward) {
            player.startMoving(MovementType.UP);
        } else if(key == left) {
            player.startMoving(MovementType.LEFT);
        } else if(key == backward) {
            player.startMoving(MovementType.DOWN);
        } else if(key == right) {
            player.startMoving(MovementType.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase().charAt(0);

        char forward = (char) Settings.getSetting("forward");
        char left = (char) Settings.getSetting("left");
        char backward = (char) Settings.getSetting("backward");
        char right = (char) Settings.getSetting("right");

        if(key == forward) {
            player.stopMoving(MovementType.UP);
        } else if(key == left) {
            player.stopMoving(MovementType.LEFT);
        } else if(key == backward) {
            player.stopMoving(MovementType.DOWN);
        } else if(key == right) {
            player.stopMoving(MovementType.RIGHT);
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
