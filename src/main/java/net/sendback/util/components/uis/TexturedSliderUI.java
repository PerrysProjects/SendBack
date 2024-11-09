package net.sendback.util.components.uis;

import net.sendback.util.components.TexturedSlider;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TexturedSliderUI extends BasicSliderUI {
    private final TexturedSlider slider;
    private BufferedImage middleTrackVertical, middleTrackHorizontal;

    public TexturedSliderUI(TexturedSlider slider) {
        super(slider);
        this.slider = slider;
        updateTextures();
        // Add a listener to ensure repaint while sliding
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                slider.repaint();
            }
        });
    }

    private void updateTextures() {
        if(slider.getTrackTexture() != null) {
            middleTrackVertical = extractMiddleTextureVertical(slider.getTrackTexture());
            middleTrackHorizontal = extractMiddleTextureHorizontal(slider.getTrackTexture());
        }
    }

    @Override
    public void paintTrack(Graphics g) {
        if(slider.getTrackTexture() == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        BufferedImage currentTrackTexture = slider.getTrackTexture();

        if(slider.getOrientation() == SwingConstants.HORIZONTAL) {
            int trackHeight = slider.getHeight();
            int yPos = trackRect.y + (trackRect.height - trackHeight) / 2;

            g2.drawImage(currentTrackTexture, trackRect.x, yPos, trackHeight, trackHeight, null);
            g2.drawImage(currentTrackTexture, trackRect.x + trackRect.width - trackHeight, yPos, trackHeight, trackHeight, null);
            g2.drawImage(middleTrackVertical, trackRect.x + trackHeight / 2, yPos, trackRect.width - trackHeight, trackHeight, null);
        } else {
            int trackWidth = slider.getWidth();
            int xPos = trackRect.x + (trackRect.width - trackWidth) / 2;

            g2.drawImage(currentTrackTexture, xPos, trackRect.y, trackWidth, trackWidth, null);
            g2.drawImage(currentTrackTexture, xPos, trackRect.y + trackRect.height - trackWidth, trackWidth, trackWidth, null);
            g2.drawImage(middleTrackHorizontal, xPos, trackRect.y + trackWidth / 2, trackWidth, trackRect.height - trackWidth, null);
        }

        g2.dispose();
    }

    @Override
    public void paintThumb(Graphics g) {
        if(slider.getThumbTexture() == null) return;

        BufferedImage currentThumbTexture = slider.isPressed() && slider.getClickedThumbTexture() != null
                ? slider.getClickedThumbTexture()
                : slider.getThumbTexture();

        Graphics2D g2 = (Graphics2D) g.create();
        int thumbSize = slider.getOrientation() == SwingConstants.HORIZONTAL ? slider.getHeight() : slider.getWidth();

        // Center the thumb texture on the track
        int thumbX = thumbRect.x + (thumbRect.width - thumbSize) / 2;
        int thumbY = thumbRect.y + (thumbRect.height - thumbSize) / 2;

        g2.drawImage(currentThumbTexture, thumbX, thumbY, thumbSize, thumbSize, null);
        g2.dispose();
    }

    private BufferedImage extractMiddleTextureVertical(BufferedImage img) {
        return img.getSubimage(img.getWidth() / 2, 0, 1, img.getHeight());
    }

    private BufferedImage extractMiddleTextureHorizontal(BufferedImage img) {
        return img.getSubimage(0, img.getHeight() / 2, img.getWidth(), 1);
    }
}
