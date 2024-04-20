package net.throwback.worlds;

import net.throwback.objects.TileObject;
import net.throwback.objects.objectId.ObjectId;
import net.throwback.util.procedural.PerlinNoise;

public class World {
    private final String name;

    private final int width;
    private final int height;
    private final TileObject[][] grid;
    private final TileObject borderTile;

    private final int size;
    private final int seed;

    public World(String name, int width, int height, int size, int seed) {
        this.name = name;

        this.width = width;
        this.height = height;
        grid = new TileObject[width][height];
        borderTile = new TileObject(-1, -1, ObjectId.EXAMPLE);

        this.size = size;
        this.seed = seed;

        generateWorld();
    }

    private void generateWorld() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                double value = PerlinNoise.noise(x, y, 0.0, size, seed);
                if(value < 0.3 && value > -0.3) {
                    grid[x][y] = new TileObject(x, y, ObjectId.GRASS);
                } else {
                    grid[x][y] = new TileObject(x, y, ObjectId.STONE);
                }
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

    public TileObject[][] getGrid() {
        return grid;
    }

    public TileObject getBorderTile() {
        return borderTile;
    }

    public int getSeed() {
        return seed;
    }

    public int getSize() {
        return size;
    }
}
