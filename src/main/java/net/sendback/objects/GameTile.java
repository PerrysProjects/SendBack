package net.sendback.objects;

import net.sendback.objects.ids.TileID;

import java.awt.image.BufferedImage;

public abstract class GameTile {
    private final int x, y;
    private final TileID id;
    private final BufferedImage[] textures;
    private final double width, height;
    private final boolean solid;
    private final boolean rotateX, rotateY;

    public GameTile(int x, int y, TileID id) {
        this.x = x;
        this.y = y;
        this.id = id;
        textures = id.getTextures();
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

    public TileID getId() {
        return id;
    }

    public BufferedImage[] getTextures() {
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
