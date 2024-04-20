package net.throwback.objects.objectId;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public enum ObjectId {
    EXAMPLE("example", new BufferedImage(6, 6, Image.SCALE_DEFAULT));

    private String name;

    ObjectId(String name, Image image) {
    this.name = name;
getObjectImage();

    }

    public String getName() {
        return name;
    }
    public BufferedImage tree1, stone1;

    public void getObjectImage() {
        try {
    tree1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objectImages/tree1.png")));
    stone1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objectImages/stone1.png")));
        } catch (IOException e){
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

    }
}

