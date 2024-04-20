package net.throwback.objects.objectId;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum ObjectId {
    EXAMPLE(-1, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    GRASS(0, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    BAUM1(1, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)},true, false, 16, 16, true),
    STEIN1(2, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true);
    // ("/objectImages/tree1.png")  beispiel, weil, warum nicht

    private final int id;
    private final Image[] textures;
    private final boolean rotateX, rotateY;

    private final int height, width;
    private final boolean solid;

    ObjectId(int id, Image[] textures,boolean rotateX, boolean rotateY, int height, int width, boolean solid) {
        this.id = id;
        this.textures = textures;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.height = height;
        this.width = width;
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

    public boolean getIsSolid() {
        return solid;
    }

    public boolean getIsRotatedX() {
        return rotateX;
    }

    public boolean getIsRotatedY() {
        return rotateY;
    }
}





