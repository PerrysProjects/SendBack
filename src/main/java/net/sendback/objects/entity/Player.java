package net.sendback.objects.entity;

import net.sendback.objects.item.Item;
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
    private boolean moving, movingUp, movingLeft, movingDown, movingRight;

    private double speed;

    private Item[][] inventory;
    private boolean inventoryOpen;

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

        inventory = new Item[4][3];
        inventoryOpen = false;
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

                    movingUp = !(newY < 0) && (world.getWorldTileGrid()[floorX][floorY] == null || !world.getWorldTileGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldTileGrid()[ceilX][floorY] == null || !world.getWorldTileGrid()[ceilX][floorY].isSolid());
                }
            }
            case LEFT -> {
                if(x > 0) {
                    double newX = x - speed;
                    int floorX = (int) Math.floor(newX);
                    int floorY = (int) Math.floor(y);
                    int ceilY = (int) Math.ceil(y);

                    movingLeft = !(newX < 0) && (world.getWorldTileGrid()[floorX][floorY] == null || !world.getWorldTileGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldTileGrid()[floorX][ceilY] == null || !world.getWorldTileGrid()[floorX][ceilY].isSolid());
                }
            }
            case DOWN -> {
                if(y < worldHeight - 1) {
                    double newY = y + speed;
                    int floorX = (int) Math.floor(x);
                    int floorY = (int) Math.floor(newY + 1);
                    int ceilX = (int) Math.ceil(x);

                    movingDown = !(newY > worldHeight - 1) && (world.getWorldTileGrid()[floorX][floorY] == null || !world.getWorldTileGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldTileGrid()[ceilX][floorY] == null || !world.getWorldTileGrid()[ceilX][floorY].isSolid());
                }
            }
            case RIGHT -> {
                if(x < worldWidth - 1) {
                    double newX = x + speed;
                    int floorX = (int) Math.floor(newX + 1);
                    int floorY = (int) Math.floor(y);
                    int ceilY = (int) Math.ceil(y);

                    movingRight = !(newX > worldWidth - 1) && (world.getWorldTileGrid()[floorX][floorY] == null || !world.getWorldTileGrid()[floorX][floorY].isSolid()) &&
                            (world.getWorldTileGrid()[floorX][ceilY] == null || !world.getWorldTileGrid()[floorX][ceilY].isSolid());
                }
            }
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
        x = Math.floor(x * 10000) / 10000.0;
        y = Math.floor(y * 10000) / 10000.0;

        World currentWorld = session.getCurrentWorld();
        int worldHeight = world.getHeight();
        int worldWidth = world.getWidth();

        if(world != currentWorld) {
            world = currentWorld;
        }

        if(y > 0) {
            if(movingUp) {
                double newY = y - speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY);
                int ceilX = (int) Math.ceil(x);

                if(newY < 0 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[ceilX][floorY] != null && world.getWorldTileGrid()[ceilX][floorY].isSolid())) {
                    y = floorY + 1;
                } else {
                    y = newY;
                }
            } else if(y != Math.floor(y) && !movingDown) {
                double newY = y - speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY);
                int ceilX = (int) Math.ceil(x);

                if(newY < 0 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[ceilX][floorY] != null && world.getWorldTileGrid()[ceilX][floorY].isSolid())) {
                    y = floorY + 1;
                }
            }
        }

        if(x > 0) {
            if(movingLeft) {
                double newX = x - speed;
                int floorX = (int) Math.floor(newX);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX < 0 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[floorX][ceilY] != null && world.getWorldTileGrid()[floorX][ceilY].isSolid())) {
                    x = floorX + 1;
                } else {
                    x = newX;
                }
            } else if(x != Math.floor(x) && !movingRight) {
                double newX = x - speed;
                int floorX = (int) Math.floor(newX);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX < 0 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[floorX][ceilY] != null && world.getWorldTileGrid()[floorX][ceilY].isSolid())) {
                    x = floorX + 1;
                }
            }
        }

        if(y < worldHeight - 1) {
            if(movingDown) {
                double newY = y + speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY + 1);
                int ceilX = (int) Math.ceil(x);

                if(newY > worldHeight - 1 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[ceilX][floorY] != null && world.getWorldTileGrid()[ceilX][floorY].isSolid())) {
                    y = floorY - 1;
                } else {
                    y = newY;
                }
            } else if(y != Math.floor(y) && !movingUp) {
                double newY = y + speed;
                int floorX = (int) Math.floor(x);
                int floorY = (int) Math.floor(newY + 1);
                int ceilX = (int) Math.ceil(x);

                if(newY > worldHeight - 1 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[ceilX][floorY] != null && world.getWorldTileGrid()[ceilX][floorY].isSolid())) {
                    y = floorY - 1;
                }
            }
        }

        if(x < worldWidth - 1) {
            if(movingRight) {
                double newX = x + speed;
                int floorX = (int) Math.floor(newX + 1);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX > worldWidth - 1 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[floorX][ceilY] != null && world.getWorldTileGrid()[floorX][ceilY].isSolid())) {
                    x = floorX - 1;
                } else {
                    x = newX;
                }
            } else if(x != Math.floor(x) && !movingLeft) {
                double newX = x + speed;
                int floorX = (int) Math.floor(newX + 1);
                int floorY = (int) Math.floor(y);
                int ceilY = (int) Math.ceil(y);

                if(newX > worldWidth - 1 || (world.getWorldTileGrid()[floorX][floorY] != null && world.getWorldTileGrid()[floorX][floorY].isSolid()) ||
                        (world.getWorldTileGrid()[floorX][ceilY] != null && world.getWorldTileGrid()[floorX][ceilY].isSolid())) {
                    x = floorX - 1;
                }
            }
        }

        moving = movingUp || movingLeft || movingDown || movingRight;

        if(moving) {
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
        if(movingUp && movingLeft && !movingRight && !movingDown) {
            return sprite.getUpLeftTexture();
        } else if(movingUp && movingRight && !movingLeft && !movingDown) {
            return sprite.getUpRightTexture();
        } else if(movingDown && movingLeft && !movingRight && !movingUp) {
            return sprite.getDownLeftTexture();
        } else if(movingDown && movingRight && !movingLeft && !movingUp) {
            return sprite.getDownRightTexture();
        } else if(movingUp && !movingDown) {
            return sprite.getUpTexture();
        } else if(movingLeft && !movingRight) {
            return sprite.getLeftTexture();
        } else if(movingRight && !movingLeft) {
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

    public Item[][] getInventory() {
        return inventory;
    }

    public boolean isInventoryOpen() {
        return inventoryOpen;
    }

    public void toggleInventory() {
        inventoryOpen = !inventoryOpen;
        System.out.println(inventoryOpen);
    }
}
