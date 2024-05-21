package net.sendback.util;

import net.sendback.Main;
import net.sendback.objects.entity.Player;
import net.sendback.worlds.World;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class Session implements Runnable {
    private final String name;
    private final Path path;
    private final Date creationDate;
    private final Date lastUse;

    private final int seed;
    private final World[] worlds;
    private final World currentWorld;

    private final Player player;

    private final Thread thread;
    private final int tps;

    public Session(String name, int seed) {
        this.name = name;
        path = Paths.get(Main.getPath() + "/" + name);
        creationDate = new Date();
        lastUse = new Date();

        this.seed = seed;
        worlds = new World[]{new World("Test", 500, 500, 20, seed)};
        currentWorld = worlds[0];

        player = new Player(this,80, 80);

        thread = new Thread(this, name);
        tps = 20;
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    private void update() {
        /*for(World world : worlds) {
            world.update();
        }*/

        player.update();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/tps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while(thread.isAlive()) {
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
