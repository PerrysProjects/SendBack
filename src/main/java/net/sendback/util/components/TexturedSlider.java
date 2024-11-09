package net.sendback.util.components;

import net.sendback.util.components.uis.TexturedSliderUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TexturedSlider extends JSlider {
    private BufferedImage trackTexture;
    private BufferedImage thumbTexture;
    private BufferedImage clickedThumbTexture;

    private Color foregroundColor;
    private Color clickedForegroundColor;

    private boolean pressed;

    public TexturedSlider(int orientation, BufferedImage trackTexture, BufferedImage thumbTexture) {
        super(orientation);
        this.trackTexture = trackTexture;
        this.thumbTexture = thumbTexture;

        foregroundColor = getForeground();
        clickedForegroundColor = foregroundColor;

        setOpaque(false);
        setUI(new TexturedSliderUI(this));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setForeground(clickedForegroundColor);
                pressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setForeground(foregroundColor);
                pressed = false;
                repaint();
            }
        });
    }

    public void setTrackTexture(BufferedImage trackTexture) {
        this.trackTexture = trackTexture;
    }

    public void setThumbTexture(BufferedImage thumbTexture) {
        this.thumbTexture = thumbTexture;
    }

    public void setClickedThumbTexture(BufferedImage clickedThumbTexture) {
        this.clickedThumbTexture = clickedThumbTexture;
    }

    public void setClickedForegroundColor(Color color) {
        this.clickedForegroundColor = color;
    }

    public BufferedImage getTrackTexture() {
        return trackTexture;
    }

    public BufferedImage getThumbTexture() {
        return thumbTexture;
    }

    public BufferedImage getClickedThumbTexture() {
        return clickedThumbTexture;
    }

    public boolean isPressed() {
        return pressed;
    }
}
