package net.sendback.util.components;

import net.sendback.util.components.listener.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TexturedButton extends JButton {
    private BufferedImage texture;
    private BufferedImage middleTextureVertical;
    private BufferedImage middleTextureHorizontal;

    private BufferedImage clickedTexture;
    private BufferedImage clickedMiddleTextureVertical;
    private BufferedImage clickedMiddleTextureHorizontal;

    private String text;
    private String clickedText;
    private int fontOffset;
    private int clickedFontOffset;
    private Color unclicedForeground;
    private Color clickedForeground = unclicedForeground;

    public TexturedButton(BufferedImage texture) {
        this(texture, null);
    }

    public TexturedButton(BufferedImage texture, String text) {
        this.texture = texture;
        this.middleTextureVertical = extractMiddleTextureVertical(texture);
        this.middleTextureHorizontal = extractMiddleTextureHorizontal(texture);

        this.text = text;
        unclicedForeground = getForeground();

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        addMouseListener(new MouseHandler());
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(texture == null) return;

        int buttonWidth = getWidth();
        int buttonHeight = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        BufferedImage currentTexture = getModel().isPressed() && clickedTexture != null ? clickedTexture : texture;
        BufferedImage currentMiddleVertical = getModel().isPressed() && clickedMiddleTextureVertical != null ? clickedMiddleTextureVertical : middleTextureVertical;
        BufferedImage currentMiddleHorizontal = getModel().isPressed() && clickedMiddleTextureHorizontal != null ? clickedMiddleTextureHorizontal : middleTextureHorizontal;

        if(buttonWidth > buttonHeight) {
            g2.drawImage(currentTexture, 0, 0, buttonHeight, buttonHeight, null);
            g2.drawImage(currentTexture, buttonWidth - buttonHeight, 0, buttonHeight, buttonHeight, null);
            g2.drawImage(currentMiddleVertical, buttonHeight / 2, 0, buttonWidth - buttonHeight, buttonHeight, null);
        } else if(buttonHeight > buttonWidth) {
            g2.drawImage(currentTexture, 0, 0, buttonWidth, buttonWidth, null);
            g2.drawImage(currentTexture, 0, buttonHeight - buttonWidth, buttonWidth, buttonWidth, null);
            g2.drawImage(currentMiddleHorizontal, 0, buttonWidth / 2, buttonWidth, buttonHeight - buttonWidth, null);
        } else {
            g2.drawImage(currentTexture, 0, 0, buttonWidth, buttonHeight, null);
        }

        String displayText = getModel().isPressed() && clickedText != null ? clickedText : text;
        int offset = getModel().isPressed() ? clickedFontOffset : fontOffset;

        if(displayText != null && !displayText.isEmpty()) {
            Color foreground = getModel().isPressed() ? clickedForeground : unclicedForeground;

            g2.setColor(foreground);
            g2.setFont(getFont());
            FontMetrics metrics = g2.getFontMetrics();
            int textX = (buttonWidth - metrics.stringWidth(displayText)) / 2;
            int textY = (buttonHeight - metrics.getHeight()) / 2 + metrics.getAscent() + offset;
            g2.drawString(displayText, textX, textY);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Override to remove border painting for a cleaner custom look
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
        this.middleTextureVertical = extractMiddleTextureVertical(texture);
        this.middleTextureHorizontal = extractMiddleTextureHorizontal(texture);
    }

    public BufferedImage getClickedTexture() {
        return clickedTexture;
    }

    public void setClickedTexture(BufferedImage clickedTexture) {
        this.clickedTexture = clickedTexture;
        this.clickedMiddleTextureVertical = extractMiddleTextureVertical(clickedTexture);
        this.clickedMiddleTextureHorizontal = extractMiddleTextureHorizontal(clickedTexture);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    public String getClickedText() {
        return clickedText;
    }

    public void setClickedText(String clickedText) {
        this.clickedText = clickedText;
    }

    public int getFontOffset() {
        return fontOffset;
    }

    public void setFontOffset(int fontOffset) {
        this.fontOffset = fontOffset;
    }

    public int getClickedFontOffset() {
        return clickedFontOffset;
    }

    public void setClickedFontOffset(int clickedFontOffset) {
        this.clickedFontOffset = clickedFontOffset;
    }

    @Override
    public void setForeground(Color fg) {
        unclicedForeground = fg;
        super.setForeground(fg);
    }

    public Color getClickedForeground() {
        return clickedForeground;
    }

    public void setClickedForeground(Color clickedForeground) {
        this.clickedForeground = clickedForeground;
    }

    private BufferedImage extractMiddleTextureVertical(BufferedImage img) {
        return img.getSubimage(img.getWidth() / 2, 0, 1, img.getHeight());
    }

    private BufferedImage extractMiddleTextureHorizontal(BufferedImage img) {
        return img.getSubimage(0, img.getHeight() / 2, img.getWidth(), 1);
    }
}
