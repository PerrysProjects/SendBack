package net.gts_projekt;

import java.nio.file.Path;

public class Session implements Runnable {
    private String name;
    private Path path;

    private Thread thread;
    private int tps;

    public Session(String name) {
        this.name = name;

        initialize();
    }

    /*
    * This Contructor will be finished when the saving system is implemented
    * Needs to grab the name of the session from the sessions saving file and
    * put it into the name attribute!
     */
    public Session(Path path) {
        this.path = path;

        initialize();
    }

    private void initialize() {
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
