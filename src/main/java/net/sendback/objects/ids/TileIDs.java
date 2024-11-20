package net.sendback.objects.ids;

import net.sendback.util.resources.ResourceGetter;
import net.sendback.util.resources.TileSprites;

public enum TileIDs {
    //EXAMPLE(-1, new BufferedImage[]{new BufferedImage(6, 6, Image.SCALE_DEFAULT)}, true, false, 16, 16, true),
    GRASS(0, new TileSprites(ResourceGetter.getTileTexture("grass.png")), true, false, 1, 1, 0, 0, true),
    STONE(1, new TileSprites(ResourceGetter.getTileTexture("stone.png")), true, false, 1, 1, 0, 0, true),
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
            true, false, 1, 1, 0, 0, true),
    TREE_ALONE(1, new TileSprites(ResourceGetter.getTileTexture("tree_alone.png")), true, false, 1, 1, 0, 0, true),
    LAB_WALL(1, new TileSprites(ResourceGetter.getTileTexture("lab_wall.png")), true, false, 1, 1, 0, 0, true),
    REDBUTTON_UP(1, new TileSprites(ResourceGetter.getTileTexture("redbutton_up.png")), true, false, 1, 1, 0, 0, true),
    REDBUTTON_DOWN(1, new TileSprites(ResourceGetter.getTileTexture("redbutton_down.png")), true, false, 1, 1, 0, 0, true),
    YELLOWBUTTON_UP(1, new TileSprites(ResourceGetter.getTileTexture("yellowbutton_up.png")), true, false, 1, 1, 0, 0, true),
    YELLOWBUTTON_DOWN(1, new TileSprites(ResourceGetter.getTileTexture("yellowbutton_down.png")), true, false, 1, 1, 0, 0, true),
    GREENBUTTON_UP(1, new TileSprites(ResourceGetter.getTileTexture("greenbutton_up.png")), true, false, 1, 1, 0, 0, true),
    GREENBUTTON_DOWNL(1, new TileSprites(ResourceGetter.getTileTexture("greenbutton_down.png")), true, false, 1, 1, 0, 0, true),
    BLUEBUTTON_UP(1, new TileSprites(ResourceGetter.getTileTexture("bluebutton_up.png")), true, false, 1, 1, 0, 0, true),
    BLUEBUTTON_DOWN(1, new TileSprites(ResourceGetter.getTileTexture("bluebutton_down.png")), true, false, 1, 1, 0, 0, true),
    PURPLEBUTTON_UP(1, new TileSprites(ResourceGetter.getTileTexture("purplebutton_up.png")), true, false, 1, 1, 0, 0, true),
    PRUPLEBUTTON_DOWN(1, new TileSprites(ResourceGetter.getTileTexture("purplebutton_down.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_DOWN_1(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_down_1.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_DOWN_2(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_down_2.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_DOWN_3(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_down_3.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_DOWN_4(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_down_4.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_DOWN_5(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_down_5.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_UP_1(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_up_1.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_UP_2(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_up_2.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_UP_3(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_up_3.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_UP_4(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_up_4.png")), true, false, 1, 1, 0, 0, true),
    PIPEWALL_UP_5(1, new TileSprites(ResourceGetter.getTileTexture("pipewall_up_5.png")), true, false, 1, 1, 0, 0, true),
    GRAYWALL(1, new TileSprites(ResourceGetter.getTileTexture("graywall.png")), true, false, 1, 1, 0, 0, true),
    WALL_WALL(1, new TileSprites(ResourceGetter.getTileTexture("wall_wall.png")), true, false, 1, 1, 0, 0, true),
    FLOOR_LEFT(1, new TileSprites(ResourceGetter.getTileTexture("labfloor_left.png")), true, false, 1, 1, 0, 0, true),
    FLOOR_RIGHT(1, new TileSprites(ResourceGetter.getTileTexture("labfloor_right.png")), true, false, 1, 1, 0, 0, true),
    TIME_MACHINE(1, new TileSprites(ResourceGetter.getTileTexture("time_machine.png")), true, false, 3, 3, -1, -2, true);

    private final int id;
    private final TileSprites sprites;
    private final boolean rotateX, rotateY;

    private final int height, width;
    private final int offsetX, offsetY;
    private final boolean solid;

    TileIDs(int id, TileSprites sprites, boolean rotateX, boolean rotateY, int width, int height, int offsetX, int offsetY, boolean solid) {
        this.id = id;
        this.sprites = sprites;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.solid = solid;
    }

    public int getId() {
        return id;
    }

    public TileSprites getSprites() {
        return sprites;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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





