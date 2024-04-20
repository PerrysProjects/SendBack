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
            double newY = y - speed;

            if(newY < 0) {
                y = 0;
            } else {
                int floorX = (int) Math.floor(x);
                int floorNewY = (int) Math.floor(newY);
                int ceilX = (int) Math.ceil(x);

                boolean isSolidFloor = world.getWorldGrid()[floorX][floorNewY] != null &&
                        world.getWorldGrid()[floorX][floorNewY].isSolid();
                boolean isSolidCeil = world.getWorldGrid()[ceilX][floorNewY] != null &&
                        world.getWorldGrid()[ceilX][floorNewY].isSolid();

                if(!isSolidFloor && !isSolidCeil) {
                    y = newY;
                } else {
                    y = floorNewY + 1;
                }
            }
        }

        if(movingLeft && x > 0) {
            double newX = x - speed;

            if(newX < 0) {
                x = 0;
            } else {
                int floorNewX = (int) Math.floor(newX);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                boolean isSolidLeft = world.getWorldGrid()[floorNewX][floorY] != null &&
                        world.getWorldGrid()[floorNewX][floorY].isSolid();
                boolean isSolidRight = world.getWorldGrid()[floorNewX][ceilY] != null &&
                        world.getWorldGrid()[floorNewX][ceilY].isSolid();

                if(!isSolidLeft && !isSolidRight) {
                    x = newX;
                } else {
                    x = floorNewX + 1;
                }
            }
        }

        if(movingDown && y < world.getHeight() - 1) {
            double newY = y + speed;

            if(newY > world.getHeight() - 1) {
                y = world.getHeight() - 1;
            } else {
                int floorX = (int) Math.floor(x);
                int floorNewY = (int) Math.floor(newY + 1);
                int ceilX = (int) Math.ceil(x);

                boolean isSolidFloor = world.getWorldGrid()[floorX][floorNewY] != null &&
                        world.getWorldGrid()[floorX][floorNewY].isSolid();
                boolean isSolidCeil = world.getWorldGrid()[ceilX][floorNewY] != null &&
                        world.getWorldGrid()[ceilX][floorNewY].isSolid();

                if(!isSolidFloor && !isSolidCeil) {
                    y = newY;
                } else {
                    y = floorNewY - 1;
                }
            }
        }

        if(movingRight && x < world.getWidth() - 1) {
            double newX = x + speed;

            if(newX > world.getWidth() - 1) {
                x = world.getWidth() - 1;
            } else {
                int floorNewX = (int) Math.floor(newX + 1);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                boolean isSolidLeft = world.getWorldGrid()[floorNewX][floorY] != null &&
                        world.getWorldGrid()[floorNewX][floorY].isSolid();
                boolean isSolidRight = world.getWorldGrid()[floorNewX][ceilY] != null &&
                        world.getWorldGrid()[floorNewX][ceilY].isSolid();

                if(!isSolidLeft && !isSolidRight) {
                    x = newX;
                } else {
                    x = floorNewX - 1;
                }
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
