package net.throwback.objects.objectId;

import net.throwback.util.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum ObjectId {
    EXAMPLE(-1, new Image[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    GRASS(0, new Image[]{Resources.getTileTexture("grass2.png")}, true, false, 32, 32, true),
    STONE(1, new Image[]{Resources.getTileTexture("stone1.png")},true, false, 32, 32, true),
    TREE(2, new Image[]{Resources.getTileTexture("tree.png")}, true, false, 32, 40, true);
    // ("/objectImages/tree1.png")  beispiel, weil, warum nicht

    private final int id;
    private final Image[] textures;
    private final boolean rotateX, rotateY;

    private final int height, width;
    private final boolean solid;

    ObjectId(int id, Image[] textures,boolean rotateX, boolean rotateY, int width, int height, boolean solid) {
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

    public Image[] getTextures() {
        return textures;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
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





