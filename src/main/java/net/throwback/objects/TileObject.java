package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class TileObject extends GameObject {
    protected boolean isWalkable; // in einer Metohde des Spielers sollte man nicht auf Objekte laufen können bei denen dieses Attribut false ist.

    public TileObject(int x, int y, int width, int height, ObjectId id, boolean isWalkable) {
        super(x, y, width, height, id);
        this.isWalkable = isWalkable;
    }

    public boolean getIsWalkable() {
        return isWalkable;
    }

    /*  @Override
    public void render(Graphics g) {

    } */
}