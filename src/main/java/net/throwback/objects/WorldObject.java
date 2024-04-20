package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class WorldObject extends GameObject {
    public WorldObject(int x, int y, ObjectId id) {
        super(x, y, id);
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
