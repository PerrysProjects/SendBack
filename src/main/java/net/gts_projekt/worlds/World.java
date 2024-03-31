package net.gts_projekt.worlds;

import net.gts_projekt.util.procedural.PerlinNoise;

public class World {
    private String name;

    private int width;
    private int height;
    private double[][] grid;

    private int size;
    private int seed;

    public World(String name, int width, int height, int size, int seed) {
        this.name = name;

        this.width = width;
        this.height = height;
        grid = new double[width][height];

        this.size = size;
        this.seed = seed;

        generateWorld();
    }

    private void generateWorld() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                grid[x][y] = PerlinNoise.noise(x, y, 0.0, size, seed);
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

    public double[][] getGrid() {
        return grid;
    }

    public int getSeed() {
        return seed;
    }

    public int getSize() {
        return size;
    }
}
