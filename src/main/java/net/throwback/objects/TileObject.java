package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class TileObject extends GameObject {
    protected boolean isWalkable;

    public TileObject(int x, int y, int width, int height, ObjectId id, ObjectId.Image image, boolean isWalkable) {
        super(x, y, width, height, id, image);
        this.isWalkable = isWalkable;
    }

    public boolean getIsWalkable() {
        return isWalkable;
    }


    /*  @Override
    public void render(Graphics g) {

    } */
}