package net.sendback.worlds;

import net.sendback.objects.TileObject;
import net.sendback.objects.WorldObject;
import net.sendback.objects.objectId.ObjectId;
import net.sendback.util.procedural.PerlinNoise;

public class World {
    private final String name;

    private final int width;
    private final int height;
    private final TileObject[][] tileGrid;
    private final TileObject borderTile;
    private final WorldObject[][] worldGrid;

    private final int size;
    private final int seed;

    public World(String name, int width, int height, int size, int seed) {
        this.name = name;

        this.width = width;
        this.height = height;
        tileGrid = new TileObject[width][height];
        borderTile = new TileObject(-1, -1, ObjectId.GRASS);
        worldGrid = new WorldObject[width][height];

        this.size = size;
        this.seed = seed;

        generateWorld();
    }

    private void generateWorld() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                double tileValue = PerlinNoise.noise(x, y, 0.0, size, seed);
                if(tileValue < 0.3 && tileValue > -0.3) {
                    tileGrid[x][y] = new TileObject(x, y, ObjectId.GRASS);
                } else {
                    tileGrid[x][y] = new TileObject(x, y, ObjectId.STONE);
                }

                /*double worldValue = PerlinNoise.noise(x, y, 2.0, size, seed);
                if(worldValue > 0.3 || worldValue < -0.3) {
                    worldGrid[x][y] = new WorldObject(x, y, ObjectId.TREE);
                }*/
            }
        }
    }

    public void update() {

    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TileObject[][] getTileGrid() {
        return tileGrid;
    }

    public TileObject getBorderTile() {
        return borderTile;
    }

    public WorldObject[][] getWorldGrid() {
        return worldGrid;
    }

    public int getSeed() {
        return seed;
    }

    public int getSize() {
        return size;
    }
}
