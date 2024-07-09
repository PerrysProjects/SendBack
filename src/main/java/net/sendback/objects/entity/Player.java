package net.sendback.objects.entity;

import net.sendback.util.Session;
import net.sendback.worlds.World;

public class Player {
    private double size;

    private Session session;
    private World world;

    private double x, y;
    private boolean movingUp, movingLeft, movingDown, movingRight;

    private double speed;

    public Player(Session session, double x, double y) {
        size = 1;

        this.session = session;
        world = session.getCurrentWorld();

        this.x = x;
        this.y = y;

        speed = 0.08;
    }

    public void startMoving(MovementType type) {
        switch(type) {
            case UP -> movingUp = true;
            case LEFT -> movingLeft = true;
            case DOWN -> movingDown = true;
            case RIGHT -> movingRight = true;
        }
    }

    public void stopMoving(MovementType type) {
        switch(type) {
            case UP -> movingUp = false;
            case LEFT -> movingLeft = false;
            case DOWN -> movingDown = false;
            case RIGHT -> movingRight = false;
        }
    }

    public boolean isMoving(MovementType type) {
        switch(type) {
            case UP -> {
                return movingUp;
            }
            case LEFT -> {
                return movingLeft;
            }
            case DOWN -> {
                return movingDown;
            }
            case RIGHT -> {
                return movingRight;
            }
            default -> {
                return false;
            }
        }
    }

    public void update() {
        World currentWorld = session.getCurrentWorld();
        int worldHeight = world.getHeight();
        int worldWidth = world.getWidth();

        if(world != currentWorld) {
            world = currentWorld;
        }

        if(movingUp && y > 0) {
            double newY = y - speed;
            int floorX = (int) Math.floor(x);
            int floorY = (int) Math.floor(newY);
            int ceilX = (int) Math.ceil(x);

            if(newY < 0 || world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid() ||
                    world.getWorldGrid()[ceilX][floorY] != null && world.getWorldGrid()[ceilX][floorY].isSolid()) {
                y = floorY + 1;
            } else {
                y = newY;
            }
        }

        if(movingLeft && x > 0) {
            double newX = x - speed;
            int floorX = (int) Math.floor(newX);
            int floorY = (int) Math.floor(y);
            int ceilY = (int) Math.ceil(y);

            if(newX < 0 || world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid() ||
                    world.getWorldGrid()[floorX][ceilY] != null && world.getWorldGrid()[floorX][ceilY].isSolid()) {
                x = floorX + 1;
            } else {
                x = newX;
            }
        }

        if(movingDown && y < worldHeight - 1) {
            double newY = y + speed;
            int floorX = (int) Math.floor(x);
            int floorY = (int) Math.floor(newY + 1);
            int ceilX = (int) Math.ceil(x);

            if(newY > worldHeight - 1 || world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid() ||
                    world.getWorldGrid()[ceilX][floorY] != null && world.getWorldGrid()[ceilX][floorY].isSolid()) {
                y = floorY - 1;
            } else {
                y = newY;
            }
        }

        if(movingRight && x < worldWidth - 1) {
            double newX = x + speed;
            int floorX = (int) Math.floor(newX + 1);
            int floorY = (int) Math.floor(y);
            int ceilY = (int) Math.ceil(y);

            if(newX > worldWidth - 1 || world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid() ||
                    world.getWorldGrid()[floorX][ceilY] != null && world.getWorldGrid()[floorX][ceilY].isSolid()) {
                x = floorX - 1;
            } else {
                x = newX;
            }
        }
    }

    public double getSize() {
        return size;
    }

    public Session getSession() {
        return session;
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }
}
