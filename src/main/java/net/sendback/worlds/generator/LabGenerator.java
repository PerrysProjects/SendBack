package net.sendback.worlds.generator;

import net.sendback.objects.FloorTile;
import net.sendback.objects.InteractiveTile;
import net.sendback.objects.WorldTile;

public class LabGenerator extends Generator {
    private final int[][] worldLayout = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 16, 18, 20, 22, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 15, 17, 19, 21, 23, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 2, 4, 2, 6, 2, 8, 2, 10, 2, 12, 2, 0, 0, 0, 0, 0, 1},
            {1, 3, 5, 3, 7, 3, 9, 3, 11, 3, 13, 3, 0, 0, -1, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    @Override
    public FloorTile getFloorTile(int x, int y) {
        if(x % 2 != 0) {
            return new FloorTile(x, y, getFloorTileIDs()[0]);
        } else {
            return new FloorTile(x, y, getFloorTileIDs()[1]);
        }
    }

    @Override
    public WorldTile getWorldTile(int x, int y) {
        if(x < worldLayout[0].length && y < worldLayout.length && worldLayout[y][x] > 0) {
            return new WorldTile(x, y, getWorldTileIDs()[worldLayout[y][x] - 1]);
        } else if(x >= worldLayout[0].length || y >= worldLayout.length) {
            return new WorldTile(x, y, getWorldTileIDs()[0]);
        }

        return null;
    }

    @Override
    public InteractiveTile getInteractiveTile(int x, int y) {
        if(x < worldLayout[0].length && y < worldLayout.length && worldLayout[y][x] == -1) {
            return new InteractiveTile(x, y, getInteractiveTileIDs()[0]);
        }
        return null;
    }
}
