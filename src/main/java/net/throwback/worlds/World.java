package net.throwback.worlds;

import net.throwback.util.procedural.PerlinNoise;

import java.awt.*;

public class World {
    private String name;

    private int width;
    private int height;
    private Color[][] grid;
    private Color borderTile;

    private int size;
    private int seed;

    public World(String name, int width, int height, int size, int seed) {
        this.name = name;

        this.width = width;
        this.height = height;
        grid = new Color[width][height];
        borderTile = Color.CYAN;

        this.size = size;
        this.seed = seed;

        generateWorld();
    }

    private void generateWorld() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                double value = PerlinNoise.noise(x, y, 0.0, size, seed);
                int rgb = (value < 0.3 && value > -0.3) ? 0 : 0x010101 * 255;
                grid[x][y] = new Color(rgb);
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

    public Color[][] getGrid() {
        return grid;
    }

    public Color getBorderTile() {
        return borderTile;
    }

    public int getSeed() {
        return seed;
    }

    public int getSize() {
        return size;
    }
}
