package net.sendback.objects.ids;

import net.sendback.util.resources.ResourceGetter;
import net.sendback.util.resources.Sprites;

public enum TileIDs {
    //EXAMPLE(-1, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    GRASS(0, new Sprites(ResourceGetter.getTileTexture("grass2.png")), true, false, 1, 1, true),
    STONE(1, new Sprites(ResourceGetter.getTileTexture("stone1.png")), true, false, 1, 1, true),
    TREE(2, new Sprites(ResourceGetter.getTileTexture("tree.png")), true, false, 1, 1, true);
    // ("/objectImages/tree1.png")  beispiel, weil, warum nicht

    private final int id;
    private final Sprites sprites;
    private final boolean rotateX, rotateY;

    private final double height, width;
    private final boolean solid;

    TileIDs(int id, Sprites sprites, boolean rotateX, boolean rotateY, double width, double height, boolean solid) {
        this.id = id;
        this.sprites = sprites;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.width = width;
        this.height = height;
        this.solid = solid;
    }

    public int getId() {
        return id;
    }

    public Sprites getSprites() {
        return sprites;
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





