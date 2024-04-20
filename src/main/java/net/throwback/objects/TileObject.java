package net.throwback.objects;

import net.throwback.objects.objectId.ObjectId;

public class TileObject extends GameObject {
    private boolean solid; // in einer Metohde des Spielers sollte man nicht auf Objekte laufen können bei denen dieses Attribut false ist.

    public TileObject(int x, int y, ObjectId id) {
        super(x, y, id);
    }
}
