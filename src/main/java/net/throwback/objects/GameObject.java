package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

import java.awt.*;

public abstract class GameObject {
    private int x, y;
    private ObjectId id;
    private Image texture;
    private int height, width;
    private boolean solid;

    public GameObject(int x, int y, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

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
    public boolean getIsSolid() {return solid; }

    public ObjectId getId() {
        return id;
    }
}
