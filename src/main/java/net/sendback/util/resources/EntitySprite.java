package net.sendback.util.resources;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class EntitySprite extends Sprites {
    private BufferedImage leftTexture;
    private BufferedImage rightTexture;
    private BufferedImage upTexture;
    private BufferedImage upLeftTexture;
    private BufferedImage upRightTexture;
    private BufferedImage downLeftTexture;
    private BufferedImage downRightTexture;

    public EntitySprite(BufferedImage downTexture, BufferedImage leftTexture, BufferedImage rightTexture, BufferedImage upTexture,
                        BufferedImage upLeftTexture, BufferedImage upRightTexture, BufferedImage downLeftTexture, BufferedImage downRightTexture) {
        super(downTexture);
        this.leftTexture = leftTexture;
        this.rightTexture = rightTexture;
        this.upTexture = upTexture;
        this.upLeftTexture = upLeftTexture;
        this.upRightTexture = upRightTexture;
        this.downLeftTexture = downLeftTexture;
        this.downRightTexture = downRightTexture;
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

    public BufferedImage getUpLeftTexture() {
        return upLeftTexture;
    }

    public BufferedImage getUpRightTexture() {
        return upRightTexture;
    }

    public BufferedImage getDownLeftTexture() {
        return downLeftTexture;
    }

    public BufferedImage getDownRightTexture() {
        return downRightTexture;
    }
}
