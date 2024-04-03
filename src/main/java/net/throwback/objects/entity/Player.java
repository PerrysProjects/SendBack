package net.throwback.objects.entity;

import net.throwback.util.Session;
import net.throwback.worlds.World;

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

        speed = 0.3;
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
        if(world != session.getCurrentWorld()) {
            world = session.getCurrentWorld();
        }

        if(movingUp && y > 0) {
            if(y - speed < 0) {
                y = 0;
            } else {
                y -= speed;
            }
        }

        if(movingLeft && x > 0) {
            if(x - speed < 0) {
                x = 0;
            } else {
                x -= speed;
            }
        }

        if(movingDown && y < world.getHeight() - 1) {
            if(y + speed > world.getHeight() - 1) {
                y = world.getHeight() - 1;
            } else {
                y += speed;
            }
        }

        if(movingRight && x < world.getWidth() - 1) {
            if(x + speed > world.getWidth() - 1) {
                x = world.getWidth() - 1;
            } else {
                x += speed;
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
