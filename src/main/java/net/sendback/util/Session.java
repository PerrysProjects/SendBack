package net.sendback.util;

import net.sendback.Main;
import net.sendback.objects.entity.Player;
import net.sendback.worlds.World;
import net.sendback.worlds.generator.GeneratorPresets;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class Session implements Runnable {
    private final String name;
    private final Path path;
    private final Date creationDate;
    private final Date lastUse;

    private final Player player;

    private final int seed;
    private final World[] worlds;
    private World currentWorld;

    private final Thread thread;
    private final int tps;

    private boolean paused;
    private final Object lock = new Object();

    public Session(String name, int seed) {
        this.name = name;
        path = Paths.get(Main.getPath() + "/" + name);
        creationDate = new Date();
        lastUse = new Date();

        player = new Player(this, 8, 8);

        this.seed = seed;
        worlds = new World[]{new World("Lab", 500, 500, seed, GeneratorPresets.LAB, this),
                new World("StoneAge", 500, 500, seed, GeneratorPresets.FOREST, this)};
        currentWorld = worlds[0];

        thread = new Thread(this, name);
        tps = 40;
    }

    private void update() {
        getCurrentWorld().update();

        player.update();
    }

    public void start() {
        player.setUp();

        if(paused) {
            resume();
        } else if(!thread.isAlive()) {
            thread.start();
        }
    }

    public void stop() {
        thread.interrupt();
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

    @Override
    public void run() {
        double drawInterval = (double) 1000 / tps;
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

            while(System.currentTimeMillis() - lastTime < drawInterval) {
                Thread.yield();
            }
        }
    }

    public String getName() {
        return name;
    }

    public Path getPath() {
        return path;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastUse() {
        return lastUse;
    }

    public int getSeed() {
        return seed;
    }

    public World[] getWorlds() {
        return worlds;
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public Player getPlayer() {
        return player;
    }

    public Thread getThread() {
        return thread;
    }

    public int getTps() {
        return tps;
    }
}
