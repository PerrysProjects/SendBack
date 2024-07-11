package net.sendback.objects;

import net.sendback.objects.ids.TileIDs;
import net.sendback.util.resources.Sprites;

public abstract class GameTile {
    private final int x, y;
    private final TileIDs id;
    private final Sprites textures;
    private final double width, height;
    private final boolean solid;
    private final boolean rotateX, rotateY;

    public GameTile(int x, int y, TileIDs id) {
        this.x = x;
        this.y = y;
        this.id = id;
        textures = id.getSprites();
        width = id.getWidth();
        height = id.getHeight();
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

    public Sprites getTextures() {
        return textures;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
