package net.sendback.worlds.generator;

import net.sendback.objects.FloorTile;
import net.sendback.objects.WorldTile;
import net.sendback.worlds.procedural.PerlinNoise;

public class PerlinGenerator extends Generator {
    @Override
    public FloorTile getFloorTile(int x, int y) {
        double tileValue = PerlinNoise.noise(x, y, 0.0, getDensity(), getSeed());
        if(tileValue < 0.3 && tileValue > -0.3) {
            return new FloorTile(x, y, getFloorTileIDs()[0]);
        } else {
            return new FloorTile(x, y, getFloorTileIDs()[1]);
        }
    }

    @Override
    public WorldTile getWorldTile(int x, int y) {
        // Get the noise value for the current tile and its neighbors
        double tile = PerlinNoise.noise(x, y, 10.0, getDensity(), getSeed());

        double leftTile = PerlinNoise.noise(x - 1, y, 10.0, getDensity(), getSeed());
        double rightTile = PerlinNoise.noise(x + 1, y, 10.0, getDensity(), getSeed());
        double topTile = PerlinNoise.noise(x, y - 1, 10.0, getDensity(), getSeed());
        double bottomTile = PerlinNoise.noise(x, y + 1, 10.0, getDensity(), getSeed());

        double leftTopTile = PerlinNoise.noise(x - 1, y - 1, 10.0, getDensity(), getSeed());
        double rightTopTile = PerlinNoise.noise(x + 1, y - 1, 10.0, getDensity(), getSeed());
        double leftBottomTile = PerlinNoise.noise(x - 1, y + 1, 10.0, getDensity(), getSeed());
        double rightBottomTile = PerlinNoise.noise(x + 1, y + 1, 10.0, getDensity(), getSeed());

        // Check if the current tile and its neighbors are "solid" enough
        boolean validTile = Math.abs(tile) > 0.3;
        boolean validLeft = Math.abs(leftTile) > 0.3;
        boolean validRight = Math.abs(rightTile) > 0.3;
        boolean validTop = Math.abs(topTile) > 0.3;
        boolean validBottom = Math.abs(bottomTile) > 0.3;
        boolean validLeftTop = Math.abs(leftTopTile) > 0.3;
        boolean validRightTop = Math.abs(rightTopTile) > 0.3;
        boolean validLeftBottom = Math.abs(leftBottomTile) > 0.3;
        boolean validRightBottom = Math.abs(rightBottomTile) > 0.3;

        // Check if there is at least a 2x2 block of solid tiles
        boolean validBlock = (
                (validLeft && validTop && validTile && validLeftTop) ||
                        (validRight && validTop && validTile && validRightTop) ||
                        (validLeft && validBottom && validTile && validLeftBottom) ||
                        (validRight && validBottom && validTile && validRightBottom)
        );

        // If the tile and its surrounding tiles don't form a valid cluster, return null
        if(!validTile || !validBlock) {
            return null;
        }

        // If the tile is valid, create and return the corresponding WorldTile object
        return new WorldTile(x, y, getWorldTileIDs()[0]);
    }

    /*private void generate() {
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
    }*/
}
