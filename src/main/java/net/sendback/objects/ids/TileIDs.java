package net.sendback.objects.ids;

import net.sendback.util.resources.ResourceGetter;
import net.sendback.util.resources.TileSprites;

public enum TileIDs {
    //EXAMPLE(-1, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    GRASS(0, new TileSprites(ResourceGetter.getTileTexture("grass.png")), true, false, 1, 1, true),
    STONE(1, new TileSprites(ResourceGetter.getTileTexture("stone.png")), true, false, 1, 1, true),
    TREE(2, new TileSprites(ResourceGetter.getTileTexture("tree.png"),
            ResourceGetter.getTileTexture("tree_left.png"),
            ResourceGetter.getTileTexture("tree_right.png"),
            ResourceGetter.getTileTexture("tree_up.png"),
            ResourceGetter.getTileTexture("tree_down.png"),
            ResourceGetter.getTileTexture("tree_up_left.png"),
            ResourceGetter.getTileTexture("tree_up_right.png"),
            ResourceGetter.getTileTexture("tree_down_left.png"),
            ResourceGetter.getTileTexture("tree_down_right.png"),
            ResourceGetter.getTileTexture("tree_inverted_up_left.png"),
            ResourceGetter.getTileTexture("tree_inverted_up_right.png"),
            ResourceGetter.getTileTexture("tree_inverted_down_left.png"),
            ResourceGetter.getTileTexture("tree_inverted_down_right.png")),
            true, false, 1, 1, true),
    TREE_ALONE(1, new TileSprites(ResourceGetter.getTileTexture("tree_alone.png")), true, false, 1, 1, true);

    private final int id;
    private final TileSprites sprites;
    private final boolean rotateX, rotateY;

    private final double height, width;
    private final boolean solid;

    TileIDs(int id, TileSprites sprites, boolean rotateX, boolean rotateY, double width, double height, boolean solid) {
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

    public TileSprites getSprites() {
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





