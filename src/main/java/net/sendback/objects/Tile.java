package net.sendback.objects;

import net.sendback.objects.ids.TileIDs;
import net.sendback.util.resources.TileSprites;

public abstract class Tile {
    private final int x, y;
    private final TileIDs id;
    private final TileSprites textures;
    private final int width, height;
    private final int offsetX, offsetY;
    private final boolean solid;
    private final boolean rotateX, rotateY;

    public Tile(int x, int y, TileIDs id) {
        this.x = x;
        this.y = y;
        this.id = id;
        textures = id.getSprites();
        width = id.getWidth();
        height = id.getHeight();
        offsetX = id.getOffsetX();
        offsetY = id.getOffsetY();
        solid = id.isSolid();
        rotateX = id.isRotateX();
        rotateY = id.isRotateY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileIDs getId() {
        return id;
    }

    public TileSprites getTextures() {
        return textures;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isRotateX() {
        return rotateX;
    }

    public boolean isRotateY() {
        return rotateY;
    }
}
