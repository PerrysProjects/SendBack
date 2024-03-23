package net.gts_projekt.objects;

import java.awt.*;

public abstract class GameObject {
    private int x,y;
    private float width, height;
    private Image texture;

    public GameObject(int x, int y, float width, float height, Image texture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }
}
