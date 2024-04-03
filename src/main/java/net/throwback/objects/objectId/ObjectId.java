package net.throwback.objects.objectId;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum ObjectId {
    Player("Player", new BufferedImage(6, 6, Image.SCALE_DEFAULT));

    private String name;
    private Image image;

    ObjectId(String name, Image image) {

    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }
}

