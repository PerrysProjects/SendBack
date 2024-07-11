package net.sendback.objects.ids;

import java.awt.*;

public enum ItemIDs {
    STOCK(0, null, 1, 1);
    // ("/objectImages/tree1.png")  beispiel, weil, warum nicht

    private final int id;
    private final Image[] textures;
    private final double height, width;


    ItemIDs(int id, Image[] textures, double width, double height) {
        this.id = id;
        this.textures = textures;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public Image[] getTextures() {
        return textures;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
