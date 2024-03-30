package net.gts_projekt.objects;

import net.gts_projekt.objects.objectId.ObjectId;
import java.awt.*;
import java.util.LinkedList;

public class TileObject extends GameObject {
    protected boolean isSolid;

    public TileObject(int x, int y, int width, int height, ObjectId id, boolean isSolid) {
        super(x, y, width, height, id);
        this.isSolid = isSolid;
    }

    public boolean isSolid() {
        return isSolid;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {

    }
}