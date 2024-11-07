package net.sendback.worlds;

import net.sendback.objects.FloorTile;
import net.sendback.objects.WorldTile;
import net.sendback.worlds.generator.Generator;
import net.sendback.worlds.generator.GeneratorPresets;

public class World {
    private final String name;

    private final int width;
    private final int height;

    private final int seed;
    private final Generator generator;

    private final FloorTile[][] floorTileGrid;
    private final WorldTile[][] worldTileGrid;
    private final WorldTile borderTile;

    public World(String name, int width, int height, int seed, GeneratorPresets preset) {
        this.name = name;

        this.width = width;
        this.height = height;

        this.seed = seed;
        this.generator = preset.getGenerator();
        generator.setUpWorld(width, height, seed);

        floorTileGrid = new FloorTile[width][height];
        worldTileGrid = new WorldTile[width][height];
        this.borderTile = generator.getBorderTile();

        generate();
    }

    public void generate() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                floorTileGrid[x][y] = generator.getFloorTile(x, y);
                worldTileGrid[x][y] = generator.getWorldTile(x, y);
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

    public FloorTile[][] getFloorTileGrid() {
        return floorTileGrid;
    }

    public WorldTile[][] getWorldTileGrid() {
        return worldTileGrid;
    }

    public WorldTile getBorderTile() {
        return borderTile;
    }

    public int getSeed() {
        return seed;
    }
}
