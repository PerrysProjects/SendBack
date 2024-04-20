package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected ObjectId id;




    public GameObject(int x, int y, int width, int height, ObjectId id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    /* public void render(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public ObjectId getId() {
        return id;
    }
}

