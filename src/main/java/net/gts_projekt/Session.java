package net.gts_projekt;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class Session implements Runnable {
    private String name;
    private Path path;
    private Date creationDate;
    private Date lastUse;

    private Thread thread;
    private int tps;

    public Session(String name) {
        this.name = name;
        path = Paths.get(Main.getPath() + "/" + name);
        creationDate = new Date();
        lastUse = new Date();

        thread = new Thread(this, name);
        tps = 60;
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    private void update() {

    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/tps;
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
}
