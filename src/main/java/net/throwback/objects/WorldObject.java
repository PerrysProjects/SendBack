package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class WorldObject extends GameObject {
    private boolean isCollidable; // in einer Metohde des Spielers sollte man nicht durch Objekte laufen können bei denen dieses Attribut 'true' ist.
    private boolean isDestroyed;
    private int CollisionArea = getWidth() * getHeight();

    public WorldObject(int x, int y, ObjectId id, boolean isCollidable) {
        super(x, y, id);
        this.isCollidable = isCollidable;
    }

    public boolean getIsColliding() {
        return isCollidable;
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
