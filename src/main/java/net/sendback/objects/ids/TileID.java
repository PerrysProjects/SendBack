package net.sendback.objects.ids;

import net.sendback.util.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum TileID {
    EXAMPLE(-1, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    GRASS(0, new BufferedImage[]{Resources.getTileTexture("grass2.png")}, true, false, 1, 1, true),
    STONE(1, new BufferedImage[]{Resources.getTileTexture("stone1.png")}, true, false, 1, 1, true),
    TREE(2, new BufferedImage[]{Resources.getTileTexture("tree_full_more.png")}, true, false, 1, 1, true);
    // ("/objectImages/tree1.png")  beispiel, weil, warum nicht

    private final int id;
    private final BufferedImage[] textures;
    private final boolean rotateX, rotateY;

    private final double height, width;
    private final boolean solid;

    TileID(int id, BufferedImage[] textures, boolean rotateX, boolean rotateY, double width, double height, boolean solid) {
        this.id = id;
        this.textures = textures;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.width = width;
        this.height = height;
        this.solid = solid;
    }

    public int getId() {
        return id;
    }

    public BufferedImage[] getTextures() {
        return textures;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
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





