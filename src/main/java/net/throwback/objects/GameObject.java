package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

import java.awt.*;

public abstract class GameObject {
    private final int x, y;
    private final ObjectId id;
    private Image[] textures;
    private int height, width;
    private boolean solid;
    private boolean rotateX, rotateY;


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

    public boolean isSolid() {
        return solid;
    }

    public ObjectId getId() {
        return id;
    }

    public boolean getIsRotatedX() {
        return rotateX;
    }

    public boolean getIsRotatedY() {
        return rotateY;
    }
}
