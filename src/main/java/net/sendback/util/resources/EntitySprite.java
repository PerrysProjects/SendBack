package net.sendback.util.resources;

import java.awt.image.BufferedImage;

public class EntitySprite extends Sprites {
    private BufferedImage leftTexture;
    private BufferedImage rightTexture;
    private BufferedImage upTexture;


    public EntitySprite(BufferedImage downTexture, BufferedImage leftTexture, BufferedImage rightTexture, BufferedImage upTexture) {
        super(downTexture);
        this.leftTexture = leftTexture;
        this.rightTexture = rightTexture;
        this.upTexture = upTexture;
    }

    public BufferedImage getLeftTexture() {
        return leftTexture;
    }

    public BufferedImage getRightTexture() {
        return rightTexture;
    }

    public BufferedImage getUpTexture() {
        return upTexture;
    }
}
