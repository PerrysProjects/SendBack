package net.sendback.objects.entity;

import net.sendback.util.Session;
import net.sendback.util.Settings;
import net.sendback.util.SoundManager;
import net.sendback.util.resources.EntitySprite;
import net.sendback.util.resources.ResourceGetter;
import net.sendback.worlds.World;

import java.awt.image.BufferedImage;

public class Player {
    private final double size;
    private final EntitySprite sprite;

    private final SoundManager walkSounds;

    private final Session session;
    private World world;

    private double x, y;
    private boolean isMoving, isMovingUp, isMovingLeft, isMovingDown, isMovingRight;

    private double speed;

    public Player(Session session, double x, double y) {
        size = 1;
        sprite = new EntitySprite(ResourceGetter.getPlayerTexture("professor_down.png"),
                ResourceGetter.getPlayerTexture("professor_left.png"),
                ResourceGetter.getPlayerTexture("professor_right.png"),
                ResourceGetter.getPlayerTexture("professor_up.png"),
                ResourceGetter.getPlayerTexture("professor_up_left.png"),
                ResourceGetter.getPlayerTexture("professor_up_right.png"),
                ResourceGetter.getPlayerTexture("professor_down_left.png"),
                ResourceGetter.getPlayerTexture("professor_down_right.png"));

        walkSounds = new SoundManager(ResourceGetter.getEntityWalkSounds());
        walkSounds.setRandomized(true);
        walkSounds.setVolume(Settings.getFloat("volume.player"));

        this.session = session;
        world = session.getCurrentWorld();

        this.x = x;
        this.y = y;

        speed = 0.1;
    }

    public void startMoving(MovementType type) {
        World currentWorld = session.getCurrentWorld();
        int worldHeight = world.getHeight();
        int worldWidth = world.getWidth();

        if(world != currentWorld) {
            world = currentWorld;
        }

        switch(type) {
            case UP -> {
                if(y > 0) {
                    double newY = y - speed;
                    int floorX = (int) Math.floor(x);
                    int floorY = (int) Math.floor(newY);
                    int ceilX = (int) Math.ceil(x);

                    isMovingUp = !(newY < 0) && (world.getWorldGrid()[floorX][floorY] == null || !world.getWorldGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldGrid()[ceilX][floorY] == null || !world.getWorldGrid()[ceilX][floorY].isSolid());
                }
            }
            case LEFT -> {
                if(x > 0) {
                    double newX = x - speed;
                    int floorX = (int) Math.floor(newX);
                    int floorY = (int) Math.floor(y);
                    int ceilY = (int) Math.ceil(y);

                    isMovingLeft = !(newX < 0) && (world.getWorldGrid()[floorX][floorY] == null || !world.getWorldGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldGrid()[floorX][ceilY] == null || !world.getWorldGrid()[floorX][ceilY].isSolid());
                }
            }
            case DOWN -> {
                if(y < worldHeight - 1) {
                    double newY = y + speed;
                    int floorX = (int) Math.floor(x);
                    int floorY = (int) Math.floor(newY + 1);
                    int ceilX = (int) Math.ceil(x);

                    isMovingDown = !(newY > worldHeight - 1) && (world.getWorldGrid()[floorX][floorY] == null || !world.getWorldGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldGrid()[ceilX][floorY] == null || !world.getWorldGrid()[ceilX][floorY].isSolid());
                }
            }
            case RIGHT -> {
                if(x < worldWidth - 1) {
                    double newX = x + speed;
                    int floorX = (int) Math.floor(newX + 1);
                    int floorY = (int) Math.floor(y);
                    int ceilY = (int) Math.ceil(y);

                    isMovingRight = !(newX > worldWidth - 1) && (world.getWorldGrid()[floorX][floorY] == null || !world.getWorldGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldGrid()[floorX][ceilY] == null || !world.getWorldGrid()[floorX][ceilY].isSolid());
                }
            }
        }
    }

    public void stopMoving(MovementType type) {
        switch(type) {
            case UP -> isMovingUp = false;
            case LEFT -> isMovingLeft = false;
            case DOWN -> isMovingDown = false;
            case RIGHT -> isMovingRight = false;
        }
    }

    public boolean isMoving(MovementType type) {
        switch(type) {
            case UP -> {
                return isMovingUp;
            }
            case LEFT -> {
                return isMovingLeft;
            }
            case DOWN -> {
                return isMovingDown;
            }
            case RIGHT -> {
                return isMovingRight;
            }
            default -> {
                return false;
            }
        }
    }

    public void update() {
        x = Math.floor(x * 10000) / 10000.0;
        y = Math.floor(y * 10000) / 10000.0;

        World currentWorld = session.getCurrentWorld();
        int worldHeight = world.getHeight();
        int worldWidth = world.getWidth();

        if(world != currentWorld) {
            world = currentWorld;
        }

        if(y > 0) {
            if(isMovingUp) {
                double newY = y - speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY);
                int ceilX = (int) Math.ceil(x);

                if(newY < 0 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[ceilX][floorY] != null && world.getWorldGrid()[ceilX][floorY].isSolid())) {
                    y = floorY + 1;
                } else {
                    y = newY;
                }
            } else if(y != Math.floor(y) && !isMovingDown) {
                double newY = y - speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY);
                int ceilX = (int) Math.ceil(x);

                if(newY < 0 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[ceilX][floorY] != null && world.getWorldGrid()[ceilX][floorY].isSolid())) {
                    y = floorY + 1;
                }
            }
        }

        if(x > 0) {
            if(isMovingLeft) {
                double newX = x - speed;
                int floorX = (int) Math.floor(newX);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX < 0 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[floorX][ceilY] != null && world.getWorldGrid()[floorX][ceilY].isSolid())) {
                    x = floorX + 1;
                } else {
                    x = newX;
                }
            } else if(x != Math.floor(x) && !isMovingRight) {
                double newX = x - speed;
                int floorX = (int) Math.floor(newX);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX < 0 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[floorX][ceilY] != null && world.getWorldGrid()[floorX][ceilY].isSolid())) {
                    x = floorX + 1;
                }
            }
        }

        if(y < worldHeight - 1) {
            if(isMovingDown) {
                double newY = y + speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY + 1);
                int ceilX = (int) Math.ceil(x);

                if(newY > worldHeight - 1 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[ceilX][floorY] != null && world.getWorldGrid()[ceilX][floorY].isSolid())) {
                    y = floorY - 1;
                } else {
                    y = newY;
                }
            } else if(y != Math.floor(y) && !isMovingUp) {
                double newY = y + speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY + 1);
                int ceilX = (int) Math.ceil(x);

                if(newY > worldHeight - 1 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[ceilX][floorY] != null && world.getWorldGrid()[ceilX][floorY].isSolid())) {
                    y = floorY - 1;
                }
            }
        }

        if(x < worldWidth - 1) {
            if(isMovingRight) {
                double newX = x + speed;
                int floorX = (int) Math.floor(newX + 1);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX > worldWidth - 1 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[floorX][ceilY] != null && world.getWorldGrid()[floorX][ceilY].isSolid())) {
                    x = floorX - 1;
                } else {
                    x = newX;
                }
            } else if(x != Math.floor(x) && !isMovingLeft) {
                double newX = x + speed;
                int floorX = (int) Math.floor(newX + 1);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX > worldWidth - 1 || (world.getWorldGrid()[floorX][floorY] != null && world.getWorldGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldGrid()[floorX][ceilY] != null && world.getWorldGrid()[floorX][ceilY].isSolid())) {
                    x = floorX - 1;
                }
            }
        }

        isMoving = isMovingUp || isMovingLeft || isMovingDown || isMovingRight;

        if(isMoving) {
            walkSounds.play();
        } else {
            walkSounds.pause();
        }
    }

    public double getSize() {
        return size;
    }

    public EntitySprite getSprite() {
        return sprite;
    }

    public BufferedImage getTexture() {
        if(isMovingUp && isMovingLeft && !isMovingRight && !isMovingDown) {
            return sprite.getUpLeftTexture();
        } else if(isMovingUp && isMovingRight && !isMovingLeft && !isMovingDown) {
            return sprite.getUpRightTexture();
        } else if(isMovingDown && isMovingLeft && !isMovingRight && !isMovingUp) {
            return sprite.getDownLeftTexture();
        } else if(isMovingDown && isMovingRight && !isMovingLeft && !isMovingUp) {
            return sprite.getDownRightTexture();
        } else if(isMovingUp && !isMovingDown) {
            return sprite.getUpTexture();
        } else if(isMovingLeft && !isMovingRight) {
            return sprite.getLeftTexture();
        } else if(isMovingRight && !isMovingLeft) {
            return sprite.getRightTexture();
        } else {
            return sprite.getTexture();
        }
    }

    public SoundManager getWalkSounds() {
        return walkSounds;
    }

    public Session getSession() {
        return session;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
