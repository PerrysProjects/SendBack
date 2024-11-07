package net.sendback.worlds.generator;

import net.sendback.objects.FloorTile;
import net.sendback.objects.WorldTile;
import net.sendback.objects.ids.TileIDs;

public abstract class Generator {
    private int width;
    private int height;

    private int density;

    private int seed;

    private TileIDs[] floorTileIDs;
    private TileIDs[] worldTileIDs;
    private TileIDs borderTileID;

    public void setUpGenerator(TileIDs[] floorTileIDs, TileIDs[] worldTileIDs, TileIDs borderTileID, int density) {
        this.floorTileIDs = floorTileIDs;
        this.worldTileIDs = worldTileIDs;
        this.borderTileID = borderTileID;

        this.density = density;
    }

    public void setUpWorld(int width, int height, int seed) {
        this.width = width;
        this.height = height;

        this.seed = seed;
    }

    public abstract FloorTile getFloorTile(int x, int y);

    public abstract WorldTile getWorldTile(int x, int y);

    public WorldTile getBorderTile() {
        return new WorldTile(-1, -1, borderTileID);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDensity() {
        return density;
    }

    public int getSeed() {
        return seed;
    }

    public TileIDs[] getFloorTileIDs() {
        return floorTileIDs;
    }

    public TileIDs[] getWorldTileIDs() {
        return worldTileIDs;
    }

    public TileIDs getBorderTileID() {
        return borderTileID;
    }
}
