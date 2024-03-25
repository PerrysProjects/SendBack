package net.gts_projekt.objects;

import net.gts_projekt.ObjectId.ObjectId;
import java.awt.*;
import java.util.LinkedList;

public class HarvestableObject extends InteractiveObject {
    protected int health;
    protected boolean isDestroyed;

    public HarvestableObject(int x, int y, int width, int height, ObjectId id, int health, String message) {
        super(x, y, width, height, id, true, message);
        this.health = health;
        this.isDestroyed = false;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void harvest(int amount) {
        if (health <= 0) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            isDestroyed = true;
        }
    }
}
