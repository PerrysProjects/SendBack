package net.sendback.objects.ids;

import net.sendback.util.resources.ResourceGetter;
import net.sendback.util.resources.Sprites;

public enum TileIDs {
    //EXAMPLE(-1, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    GRASS(0, new Sprites(ResourceGetter.getTileTexture("grass.png")), true, false, 1, 1, true),
    STONE(1, new Sprites(ResourceGetter.getTileTexture("stone.png")), true, false, 1, 1, true),
    TREE(2, new Sprites(ResourceGetter.getTileTexture("tree.png"),
            ResourceGetter.getTileTexture("tree_left.png"),
            ResourceGetter.getTileTexture("tree_right.png"),
            ResourceGetter.getTileTexture("tree_top.png"),
            ResourceGetter.getTileTexture("tree_bottom.png"),
            ResourceGetter.getTileTexture("tree_top_left_corner.png"),
            ResourceGetter.getTileTexture("tree_top_right_corner.png"),
            ResourceGetter.getTileTexture("tree_bottom_left_corner.png"),
            ResourceGetter.getTileTexture("tree_bottom_right_corner.png"),
            ResourceGetter.getTileTexture("tree_inverted_top_left_corner.png"),
            ResourceGetter.getTileTexture("tree_inverted_top_right_corner.png"),
            ResourceGetter.getTileTexture("tree_inverted_bottom_left_corner.png"),
            ResourceGetter.getTileTexture("tree_inverted_bottom_right_corner.png")),
            true, false, 1, 1, true);

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





