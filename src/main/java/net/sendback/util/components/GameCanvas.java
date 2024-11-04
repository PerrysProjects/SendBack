package net.sendback.util.components;

import net.sendback.objects.Tile;
import net.sendback.objects.entity.MovementType;
import net.sendback.objects.entity.Player;
import net.sendback.util.Session;
import net.sendback.util.Settings;
import net.sendback.util.SoundManager;
import net.sendback.util.resources.ResourceGetter;
import net.sendback.worlds.World;

import java.awt.*;
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

    private final SoundManager backgroundMusic;

    private BufferStrategy bufferStrategy;

    private boolean paused;
    private final Object lock = new Object();

    private GameCanvas() {
        thread = new Thread(this);
        fps = 60;
        currentFps = 0;
        lastFpsCheckTime = System.currentTimeMillis();
        frameCount = 0;

        backgroundMusic = new SoundManager(ResourceGetter.getBackgroundMusic());
        backgroundMusic.setVolume(Float.parseFloat(String.valueOf(Settings.getSetting("musicVolume"))));

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

            backgroundMusic.play();
        }
    }

    public void stop() {
        backgroundMusic.pause();
        thread.interrupt();
        session.stop();
    }

    public void pause() {
        paused = true;

        backgroundMusic.pause();
    }

    public void resume() {
        synchronized(lock) {
            paused = false;
            lock.notify();

            backgroundMusic.play();
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

    private BufferedImage getTexture(Tile[][] tileGrid, int tx, int ty) {
        Tile tile = tileGrid[tx][ty];
        if(tile.getTextures().getLeftSideTexture() != null) {
            if(tileGrid[tx - 1][ty] == null &&
                    tileGrid[tx - 1][ty - 1] == null &&
                    tileGrid[tx][ty - 1] == null) {
                return tile.getTextures().getTopLeftCornerTexture();
            } else if(tileGrid[tx - 1][ty] != null &&
                    tileGrid[tx][ty - 1] == null &&
                    tileGrid[tx + 1][ty] != null) {
                return tile.getTextures().getTopSideTexture();
            } else if(tileGrid[tx + 1][ty] == null &&
                    tileGrid[tx + 1][ty - 1] == null &&
                    tileGrid[tx][ty - 1] == null) {
                return tile.getTextures().getTopRightCornerTexture();
            } else if(tileGrid[tx][ty - 1] != null &&
                    tileGrid[tx + 1][ty] == null &&
                    tileGrid[tx][ty + 1] != null) {
                return tile.getTextures().getRightSideTexture();
            } else if(tileGrid[tx + 1][ty] == null &&
                    tileGrid[tx + 1][ty + 1] == null &&
                    tileGrid[tx][ty + 1] == null) {
                return tile.getTextures().getBottomRightCornerTexture();
            } else if(tileGrid[tx - 1][ty] != null &&
                    tileGrid[tx][ty + 1] == null &&
                    tileGrid[tx + 1][ty] != null) {
                return tile.getTextures().getBottomSideTexture();
            } else if(tileGrid[tx - 1][ty] == null &&
                    tileGrid[tx - 1][ty + 1] == null &&
                    tileGrid[tx][ty + 1] == null) {
                return tile.getTextures().getBottomLeftCornerTexture();
            } else if(tileGrid[tx][ty - 1] != null &&
                    tileGrid[tx - 1][ty] == null &&
                    tileGrid[tx][ty + 1] != null) {
                return tile.getTextures().getLeftSideTexture();
            } else if(tileGrid[tx][ty - 1] != null &&
                    tileGrid[tx + 1][ty - 1] == null &&
                    tileGrid[tx + 1][ty] != null) {
                return tile.getTextures().getInvertedBottomLeftCornerTexture();
            } else if(tileGrid[tx][ty - 1] != null &&
                    tileGrid[tx - 1][ty - 1] == null &&
                    tileGrid[tx - 1][ty] != null) {
                return tile.getTextures().getInvertedBottomRightCornerTexture();
            } else if(tileGrid[tx][ty + 1] != null &&
                    tileGrid[tx + 1][ty + 1] == null &&
                    tileGrid[tx + 1][ty] != null) {
                return tile.getTextures().getInvertedTopLeftCornerTexture();
            } else if(tileGrid[tx][ty + 1] != null &&
                    tileGrid[tx - 1][ty + 1] == null &&
                    tileGrid[tx - 1][ty] != null) {
                return tile.getTextures().getInvertedTopRightCornerTexture();
            } else {
                return tile.getTextures().getTexture();
            }

        } else {
            return tile.getTextures().getTexture();
        }
    }

    private void render() {
        if(bufferStrategy != null) {
            Graphics2D g2 = (Graphics2D) bufferStrategy.getDrawGraphics();

            g2.clearRect(0, 0, getWidth(), getHeight());

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
                    int tilePosX = (int) (player.getX() - tileScreenWidth / 2 + x);
                    int tilePosY = (int) (player.getY() - tileScreenHeight / 2 + y);

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

                    //drawTile(g2, world.getTileGrid(), world.getBorderTile(), tilePosX, tilePosY, screenPosX, screenPosY, tileSize);
                    //drawTile(g2, world.getWorldGrid(), world.getBorderWorldTile(), tilePosX, tilePosY, screenPosX, screenPosY, tileSize);

                    BufferedImage texture = world.getBorderTile().getTextures().getTexture();
                    if(tilePosX >= 0 && tilePosX < world.getWidth() && tilePosY >= 0 && tilePosY < world.getHeight()) {
                        if(world.getTileGrid()[tilePosX][tilePosY] != null) {
                            texture = getTexture(world.getTileGrid(), tilePosX, tilePosY);
                            g2.drawImage(texture, screenPosX, screenPosY, tileSize, tileSize, this);
                        }

                        if(world.getWorldGrid()[tilePosX][tilePosY] != null) {
                            texture = getTexture(world.getWorldGrid(), tilePosX, tilePosY);
                            g2.drawImage(texture, screenPosX, screenPosY, tileSize, tileSize, this);
                        }
                    } else {
                        g2.drawImage(texture, screenPosX, screenPosY, tileSize, tileSize, this);
                    }
                }
            }

            int playerX = getWidth() / 2 - tileSize / 2;
            int playerY = getHeight() / 2 - tileSize / 2;

            g2.drawImage(player.getTexture(), playerX, playerY, tileSize, tileSize, this);

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
