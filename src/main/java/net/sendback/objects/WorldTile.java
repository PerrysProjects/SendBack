package net.sendback.objects;

import net.sendback.objects.ids.TileID;

public class WorldTile extends GameTile {
    public WorldTile(int x, int y, TileID id) {
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
