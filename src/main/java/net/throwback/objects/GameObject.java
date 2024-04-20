package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

import java.awt.*;

public abstract class GameObject {
    private final int x, y;
    private final ObjectId id;
    private final Image[] textures;
    private final int height, width;
    private final boolean solid;
    private final boolean rotateX, rotateY;


    public GameObject(int x, int y, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
        textures = id.getTextures();
        height = id.getHeight();
        width = id.getWidth();
        solid = id.isSolid();
        rotateX = id.isRotateX();
        rotateY = id.isRotateY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ObjectId getId() {
        return id;
    }

    public Image[] getTextures() {
        return textures;
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

    public boolean isRotateX() {
        return rotateX;
    }

    public boolean isRotateY() {
        return rotateY;
    }
}
