package net.gts_projekt.objects;

import net.gts_projekt.objects.objectId.ObjectId;

import java.awt.Graphics;
import java.util.LinkedList;

public abstract class GameObject {

    protected int x, y;

    protected int width, height;

    protected ObjectId id;
    protected Image image;


    public GameObject(int x, int y, int width, int height, ObjectId id, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.image = image;
    }


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

