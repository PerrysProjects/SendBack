package net.sendback.util.components;

import net.sendback.objects.Tile;
import net.sendback.objects.entity.MovementType;
import net.sendback.objects.entity.Player;
import net.sendback.util.Session;
import net.sendback.util.resources.ResourceGetter;
import net.sendback.worlds.World;

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

    private final int zoom;

    private Session session;
    private World world;
    private Player player;

    private BufferStrategy bufferStrategy;

    private boolean paused;
    private final Object lock = new Object();

    private GameCanvas() {
        thread = new Thread(this);
        fps = 60;
        currentFps = 0;
        lastFpsCheckTime = System.currentTimeMillis();
        frameCount = 0;

        zoom = 14;

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

            int tileSize = 0;
            while(tileSize == 0) {
                tileSize = getWidth() / zoom;
            }

            int tileScreenWidth = zoom;
            int tileScreenHeight = getHeight() / tileSize;

            for(int x = 0; x < tileScreenWidth + 2; x++) {
                for(int y = 0; y < tileScreenHeight + 2; y++) {
                    int tilePosX = (int) (player.getX() - tileScreenWidth / 2 + x);
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
                    int screenPosY = (int) (tileSize * y - tileSize * offsetY + extraY);

                    drawTile(g2, world.getTileGrid(), world.getBorderTile(), tilePosX, tilePosY, screenPosX, screenPosY, tileSize);
                    drawTile(g2, world.getWorldGrid(), world.getBorderWorldTile(), tilePosX, tilePosY, screenPosX, screenPosY, tileSize);

                    if(tilePosX == 0 && tilePosY == 0) {
                        g2.setColor(Color.CYAN);
                        g2.fillRect(screenPosX, screenPosY, tileSize, tileSize);
                    }
                }
            }

            int playerX = getWidth() / 2 - tileSize / 2;
            int playerY = getHeight() / 2 - tileSize / 2;
            g2.drawImage(ResourceGetter.getEntityTexture("prof_noGlasses.png"), playerX, playerY, tileSize, tileSize, this);

            g2.setColor(Color.YELLOW);
            g2.drawString("FPS: " + currentFps, 50, 50);
            System.out.println(currentFps);

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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
