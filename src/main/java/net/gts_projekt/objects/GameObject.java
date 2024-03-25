package net.gts_projekt.objects;

import java.awt.*;

public abstract class GameObject {

    protected Position position;
    protected Size size;

    public GameObject(){
        position = new Position(position.getX(), position.getY());
        size = new Size(size.getWidth(), size.getHeight());
    }
    public abstract void update();
    public abstract Image getImage();
}

