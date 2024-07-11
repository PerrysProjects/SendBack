package net.sendback.util.resources;

import java.awt.image.BufferedImage;

public class Sprites {
    private final BufferedImage texture;
    private final BufferedImage leftSideTexture;
    private final BufferedImage rightSideTexture;
    private final BufferedImage topSideTexture;
    private final BufferedImage bottomSideTexture;
    private final BufferedImage topLeftCornerTexture;
    private final BufferedImage topRightCornerTexture;
    private final BufferedImage bottomLeftCornerTexture;
    private final BufferedImage bottomRightCornerTexture;
    private final BufferedImage invertedTopLeftCornerTexture;
    private final BufferedImage invertedTopRightCornerTexture;
    private final BufferedImage invertedBottomLeftCornerTexture;
    private final BufferedImage invertedBottomRightCornerTexture;

    public Sprites(BufferedImage texture) {
        this.texture = texture;
        this.leftSideTexture = null;
        this.rightSideTexture = null;
        this.topSideTexture = null;
        this.bottomSideTexture = null;
        this.topLeftCornerTexture = null;
        this.topRightCornerTexture = null;
        this.bottomLeftCornerTexture = null;
        this.bottomRightCornerTexture = null;
        this.invertedTopLeftCornerTexture = null;
        this.invertedTopRightCornerTexture = null;
        this.invertedBottomLeftCornerTexture = null;
        this.invertedBottomRightCornerTexture = null;
    }

    public Sprites(BufferedImage texture, BufferedImage leftSideTexture, BufferedImage rightSideTexture, BufferedImage topSideTexture, BufferedImage bottomSideTexture,
                   BufferedImage topLeftCornerTexture, BufferedImage topRightCornerTexture, BufferedImage bottomLeftCornerTexture, BufferedImage bottomRightCornerTexture,
                   BufferedImage invertedTopLeftCornerTexture, BufferedImage invertedTopRightCornerTexture, BufferedImage invertedBottomLeftCornerTexture, BufferedImage invertedBottomRightCornerTexture) {
        this.texture = texture;
        this.leftSideTexture = leftSideTexture;
        this.rightSideTexture = rightSideTexture;
        this.topSideTexture = topSideTexture;
        this.bottomSideTexture = bottomSideTexture;
        this.topLeftCornerTexture = topLeftCornerTexture;
        this.topRightCornerTexture = topRightCornerTexture;
        this.bottomLeftCornerTexture = bottomLeftCornerTexture;
        this.bottomRightCornerTexture = bottomRightCornerTexture;
        this.invertedTopLeftCornerTexture = invertedTopLeftCornerTexture;
        this.invertedTopRightCornerTexture = invertedTopRightCornerTexture;
        this.invertedBottomLeftCornerTexture = invertedBottomLeftCornerTexture;
        this.invertedBottomRightCornerTexture = invertedBottomRightCornerTexture;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public BufferedImage getLeftSideTexture() {
        return leftSideTexture;
    }

    public BufferedImage getRightSideTexture() {
        return rightSideTexture;
    }

    public BufferedImage getTopSideTexture() {
        return topSideTexture;
    }

    public BufferedImage getBottomSideTexture() {
        return bottomSideTexture;
    }

    public BufferedImage getTopLeftCornerTexture() {
        return topLeftCornerTexture;
    }

    public BufferedImage getTopRightCornerTexture() {
        return topRightCornerTexture;
    }

    public BufferedImage getBottomLeftCornerTexture() {
        return bottomLeftCornerTexture;
    }

    public BufferedImage getBottomRightCornerTexture() {
        return bottomRightCornerTexture;
    }

    public BufferedImage getInvertedTopLeftCornerTexture() {
        return invertedTopLeftCornerTexture;
    }

    public BufferedImage getInvertedTopRightCornerTexture() {
        return invertedTopRightCornerTexture;
    }

    public BufferedImage getInvertedBottomLeftCornerTexture() {
        return invertedBottomLeftCornerTexture;
    }

    public BufferedImage getInvertedBottomRightCornerTexture() {
        return invertedBottomRightCornerTexture;
    }
}
