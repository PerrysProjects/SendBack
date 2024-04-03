package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class CollisionObject extends GameObject{

    protected int health;
    protected boolean isColliding;
    protected boolean isDestroyed;
    protected int CollisionArea = x * y;  // Vielleicht auch einfach die Koordinaten

    public CollisionObject(int x, int y, int width, int height, ObjectId id, ObjectId.Image image, Boolean isColliding) {
        super(x, y, width, height, id, image);
        this.isColliding = false;
    }

    //Konstruktor mit Leben für beispielsweise Objekte die man zerstören sollen kann

    public CollisionObject(int x, int y, int width, int height, ObjectId id, ObjectId.Image image, Boolean isColliding, int health) {
        super(x, y, width, height, id, image);
        this.isColliding = false;
        this.health = health;
    }

    public boolean getIsColliding() {
        return isColliding;
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

 /*   @Override
    public void render(Graphics g) {

    } */
}
