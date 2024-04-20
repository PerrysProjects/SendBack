package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class CollisionObject extends GameObject {
    protected int health;
    protected boolean isCollidable; // in einer Metohde des Spielers sollte man nicht durch Objekte laufen können bei denen dieses Attribut 'true' ist.
    protected boolean isDestroyed;
    protected int CollisionArea = width * height;

    public CollisionObject(int x, int y, int width, int height, ObjectId id, Boolean isColliding) {
        super(x, y, width, height, id);
        this.isCollidable = false;
    }

    //Konstruktor mit Leben für beispielsweise Objekte die man zerstören sollen kann
    public CollisionObject(int x, int y, int width, int height, ObjectId id, Boolean isColliding, int health) {
        super(x, y, width, height, id);
        this.isCollidable = false;
        this.health = health;
    }

    public boolean getIsColliding() {
        return isCollidable;
    }

    public int getHealth() {
        return health;
    }

    public boolean getIsDestroyed() {
        return isDestroyed;
    }

/* Ist im Gulag
public void harvest(int amount) {
        if (health <= 0) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            isDestroyed = true;
        }
    }
 */
}
