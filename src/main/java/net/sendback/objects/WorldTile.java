package net.sendback.objects;

import net.sendback.objects.ids.TileIDs;

public class WorldTile extends Tile {
    public WorldTile(int x, int y, TileIDs id) {
        super(x, y, id);
    }

/* Ist im Gulag
public void harvest(int amount) {
        if (health <= 0) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            isDestroyed = true;
        }
    }
 */
}
