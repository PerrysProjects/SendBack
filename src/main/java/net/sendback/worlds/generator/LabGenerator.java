package net.sendback.worlds.generator;

import net.sendback.objects.FloorTile;
import net.sendback.objects.WorldTile;
import net.sendback.objects.ids.TileIDs;

public class LabGenerator extends Generator {
    private int[][] worldLayout = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    @Override
    public FloorTile getFloorTile(int x, int y) {
        return new FloorTile(x, y, getFloorTileIDs()[0]);
    }

    @Override
    public WorldTile getWorldTile(int x, int y) {
        if(x < worldLayout.length && y < worldLayout[0].length && worldLayout[x][y] > 0) {
            return new WorldTile(x, y, getWorldTileIDs()[0]);
        } else if(x >= worldLayout.length || y >= worldLayout[0].length) {
            return new WorldTile(x, y, getWorldTileIDs()[0]);
        }

        return null;
    }
}
