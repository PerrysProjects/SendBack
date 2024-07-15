package net.sendback.worlds;

import net.sendback.objects.FloorTile;
import net.sendback.objects.WorldTile;
import net.sendback.objects.ids.TileIDs;
import net.sendback.util.procedural.PerlinNoise;

public class World {
    private final String name;

    private final int width;
    private final int height;
    private final FloorTile[][] tileGrid;
    private final FloorTile borderTile;
    private final WorldTile[][] worldGrid;
    private final WorldTile borderWorldTile;

    private final int size;
    private final int seed;

    public World(String name, int width, int height, int size, int seed) {
        this.name = name;

        this.width = width;
        this.height = height;
        tileGrid = new FloorTile[width][height];
        borderTile = new FloorTile(-1, -1, TileIDs.GRASS);
        worldGrid = new WorldTile[width][height];
        borderWorldTile = new WorldTile(-1, -1, TileIDs.TREE);

        this.size = size;
        this.seed = seed;

        generate();
    }

    private void generate() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                double tileValue = PerlinNoise.noise(x, y, 0.0, size, seed);
                if(tileValue < 0.3 && tileValue > -0.3) {
                    tileGrid[x][y] = new FloorTile(x, y, TileIDs.GRASS);
                } else {
                    tileGrid[x][y] = new FloorTile(x, y, TileIDs.STONE);
                }

                double worldValue = PerlinNoise.noise(x, y, 10.0, size, seed);
                if(worldValue > 0.3 || worldValue < -0.3 || x < 5 || x > width - 5 || y < 5 || y > height - 5) {
                    worldGrid[x][y] = new WorldTile(x, y, TileIDs.TREE);
                }
            }
        }

        for(int x = 1; x < width - 1; x++) {
            for(int y = 1; y < height - 1; y++) {
                WorldTile worldObject = worldGrid[x][y];
                WorldTile leftTopTile = worldGrid[x - 1][y - 1];
                WorldTile rightTopTile  = worldGrid[x + 1][y - 1];
                WorldTile leftBottomTile = worldGrid[x - 1][y + 1];
                WorldTile rightBottomTile = worldGrid[x + 1][y + 1];

                if(worldObject != null) {
                    if(leftTopTile == null && rightBottomTile == null) {
                        worldGrid[x][y] = null;
                    } else if(rightTopTile == null && leftBottomTile == null) {
                        worldGrid[x][y] = null;
                    }
                }
            }
        }

        for(int x = 1; x < width - 1; x++) {
            for(int y = 1; y < height - 1; y++) {
                WorldTile worldObject = worldGrid[x][y];
                WorldTile leftTile = worldGrid[x - 1][y];
                WorldTile rightTile = worldGrid[x + 1][y];
                WorldTile topTile = worldGrid[x][y - 1];
                WorldTile bottomTile = worldGrid[x][y + 1];

                if(worldObject != null) {
                    if(leftTile == null && rightTile == null) {
                        worldGrid[x][y] = null;
                    } else if(topTile == null && bottomTile == null) {
                        worldGrid[x][y] = null;
                    }
                }
            }
        }

        for(int x = 1; x < width - 1; x++) {
            for(int y = 1; y < height - 1; y++) {
                WorldTile worldObject = worldGrid[x][y];
                WorldTile leftTile = worldGrid[x - 1][y];
                WorldTile rightTile  = worldGrid[x + 1][y];
                WorldTile topTile = worldGrid[x][y - 1];
                WorldTile bottomTile = worldGrid[x][y + 1];

                if(worldObject == null) {
                    if(leftTile != null && rightTile != null && leftTile.getId().equals(rightTile.getId())) {
                        worldGrid[x][y] = new WorldTile(x, y, leftTile.getId());
                    } else if(topTile != null && bottomTile != null && bottomTile.getId().equals(topTile.getId())) {
                        worldGrid[x][y] = new WorldTile(x, y, bottomTile.getId());
                    }
                }
            }
        }

        for(int x = 1; x < width - 1; x++) {
            for(int y = 1; y < height - 1; y++) {
                WorldTile worldObject = worldGrid[x][y];
                WorldTile leftTile = worldGrid[x - 1][y];
                WorldTile rightTile = worldGrid[x + 1][y];
                WorldTile topTile = worldGrid[x][y - 1];
                WorldTile bottomTile = worldGrid[x][y + 1];

                if(worldObject != null) {
                    if(leftTile == null && rightTile == null) {
                        worldGrid[x][y] = null;
                    } else if(topTile == null && bottomTile == null) {
                        worldGrid[x][y] = null;
                    }
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

    public FloorTile[][] getTileGrid() {
        return tileGrid;
    }

    public FloorTile getBorderTile() {
        return borderTile;
    }

    public WorldTile[][] getWorldGrid() {
        return worldGrid;
    }

    public WorldTile getBorderWorldTile() {
        return borderWorldTile;
    }

    public int getSeed() {
        return seed;
    }

    public int getSize() {
        return size;
    }
}
