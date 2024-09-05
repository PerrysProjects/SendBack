package net.sendback.util.resources;

import java.awt.image.BufferedImage;

public class Sprites {
    private BufferedImage texture;

    public Sprites(BufferedImage texture) {
        this.texture = texture;
    }

    public BufferedImage getTexture() {
        return texture;
    }
}
