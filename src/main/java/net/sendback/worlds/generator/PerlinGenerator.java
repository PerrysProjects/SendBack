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
        double tile = PerlinNoise.noise(x, y, 10.0, getDensity(), getSeed());

        double leftTile = PerlinNoise.noise(x - 1, y, 10.0, getDensity(), getSeed());
        double rightTile = PerlinNoise.noise(x + 1, y, 10.0, getDensity(), getSeed());
        double topTile = PerlinNoise.noise(x, y - 1, 10.0, getDensity(), getSeed());
        double bottomTile = PerlinNoise.noise(x, y + 1, 10.0, getDensity(), getSeed());

        double leftTopTile = PerlinNoise.noise(x - 1, y - 1, 10.0, getDensity(), getSeed());
        double rightTopTile = PerlinNoise.noise(x + 1, y - 1, 10.0, getDensity(), getSeed());
        double leftBottomTile = PerlinNoise.noise(x - 1, y + 1, 10.0, getDensity(), getSeed());
        double rightBottomTile = PerlinNoise.noise(x + 1, y + 1, 10.0, getDensity(), getSeed());

        boolean validTile = Math.abs(tile) > 0.3;
        boolean validLeft = Math.abs(leftTile) > 0.3;
        boolean validRight = Math.abs(rightTile) > 0.3;
        boolean validTop = Math.abs(topTile) > 0.3;
        boolean validBottom = Math.abs(bottomTile) > 0.3;
        boolean validLeftTop = Math.abs(leftTopTile) > 0.3;
        boolean validRightTop = Math.abs(rightTopTile) > 0.3;
        boolean validLeftBottom = Math.abs(leftBottomTile) > 0.3;
        boolean validRightBottom = Math.abs(rightBottomTile) > 0.3;

        boolean validBlock = ((validLeft && validTop && validTile && validLeftTop) ||
                        (validRight && validTop && validTile && validRightTop) ||
                        (validLeft && validBottom && validTile && validLeftBottom) ||
                        (validRight && validBottom && validTile && validRightBottom));

        if(validTile && validBlock || x < 5 || x > getWidth() - 5 || y < 5 || y > getHeight() - 5) {
            return new WorldTile(x, y, getWorldTileIDs()[0]);
        }

        return null;
    }
}
