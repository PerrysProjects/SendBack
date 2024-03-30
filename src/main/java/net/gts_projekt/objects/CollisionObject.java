package net.gts_projekt.objects;

import net.gts_projekt.objects.objectId.ObjectId;
import java.awt.*;
import java.util.LinkedList;

public class CollisionObject extends GameObject{
    protected boolean isColliding;

    public CollisionObject(int x, int y, int width, int height, ObjectId id, Boolean isColliding) {
        super(x, y, width, height, id);
        this.isColliding = false;
    }

    public boolean isColliding() {
        return isColliding;
    }
    @Override
    public void tick(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {

    }
}