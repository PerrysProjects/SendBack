package net.gts_projekt.objects;

import net.gts_projekt.objects.objectId.ObjectId;

import java.awt.Graphics;
import java.util.LinkedList;

public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;

    protected ObjectId id;

    public GameObject(int x, int y, int width, int height, ObjectId id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> object);

    public abstract void render(Graphics g);

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

