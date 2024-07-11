package net.sendback.objects.item;

import net.sendback.objects.item.ItemId;

import java.awt.*;

public class Item {
    private String name;
    private ItemId id;
    private Image[] textures;
    private double width, height;

    public Item(String name, ItemId id) {
        this.name = name;
        textures = id.getTextures();
        width = id.getWidth();
        height = id.getHeight();
    }

    public String getName() {
        return name;
    }

    public Image[] getTextures() {
        return textures;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
