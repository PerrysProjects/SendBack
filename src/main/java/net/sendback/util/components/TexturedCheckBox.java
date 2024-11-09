package net.sendback.util.components;

import net.sendback.util.components.listener.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TexturedCheckBox extends JCheckBox {
    private BufferedImage texture;
    private BufferedImage middleTextureVertical;
    private BufferedImage middleTextureHorizontal;

    private BufferedImage selectedTexture;
    private BufferedImage selectedMiddleTextureVertical;
    private BufferedImage selectedMiddleTextureHorizontal;
    private int checkmarkOffset = 0;

    public TexturedCheckBox(BufferedImage texture) {
        this(texture, null);
    }

    public TexturedCheckBox(BufferedImage texture, String text) {
        super(text);
        this.texture = texture;
        this.middleTextureVertical = extractMiddleTextureVertical(texture);
        this.middleTextureHorizontal = extractMiddleTextureHorizontal(texture);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        addMouseListener(new MouseHandler());
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(texture == null) return;

        int checkboxWidth = getWidth();
        int checkboxHeight = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        BufferedImage currentTexture = isSelected() && selectedTexture != null ? selectedTexture : texture;
        BufferedImage currentMiddleVertical = isSelected() && selectedMiddleTextureVertical != null ? selectedMiddleTextureVertical : middleTextureVertical;
        BufferedImage currentMiddleHorizontal = isSelected() && selectedMiddleTextureHorizontal != null ? selectedMiddleTextureHorizontal : middleTextureHorizontal;

        if(checkboxWidth > checkboxHeight) {
            g2.drawImage(currentTexture, 0, 0, checkboxHeight, checkboxHeight, null);
            g2.drawImage(currentTexture, checkboxWidth - checkboxHeight, 0, checkboxHeight, checkboxHeight, null);
            g2.drawImage(currentMiddleVertical, checkboxHeight / 2, 0, checkboxWidth - checkboxHeight, checkboxHeight, null);
        } else if(checkboxHeight > checkboxWidth) {
            g2.drawImage(currentTexture, 0, 0, checkboxWidth, checkboxWidth, null);
            g2.drawImage(currentTexture, 0, checkboxHeight - checkboxWidth, checkboxWidth, checkboxWidth, null);
            g2.drawImage(currentMiddleHorizontal, 0, checkboxWidth / 2, checkboxWidth, checkboxHeight - checkboxWidth, null);
        } else {
            g2.drawImage(currentTexture, 0, 0, checkboxWidth, checkboxHeight, null);
        }

        if(isSelected()) {
            g2.setColor(getForeground());
            g2.setFont(new Font("SansSerif", Font.PLAIN, Math.min(checkboxWidth, checkboxHeight) / 2));
            FontMetrics metrics = g2.getFontMetrics();
            int checkX = (checkboxWidth - metrics.stringWidth("✔")) / 2;
            int checkY = ((checkboxHeight + metrics.getAscent()) / 2) + checkmarkOffset;
            g2.drawString("✔", checkX, checkY);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
        this.middleTextureVertical = extractMiddleTextureVertical(texture);
        this.middleTextureHorizontal = extractMiddleTextureHorizontal(texture);
    }

    public BufferedImage getSelectedTexture() {
        return selectedTexture;
    }

    public void setSelectedTexture(BufferedImage selectedTexture) {
        this.selectedTexture = selectedTexture;
        this.selectedMiddleTextureVertical = extractMiddleTextureVertical(selectedTexture);
        this.selectedMiddleTextureHorizontal = extractMiddleTextureHorizontal(selectedTexture);
    }

    public int getCheckmarkOffset() {
        return checkmarkOffset;
    }

    public void setCheckmarkOffset(int checkmarkOffset) {
        this.checkmarkOffset = checkmarkOffset;
        repaint();
    }

    private BufferedImage extractMiddleTextureVertical(BufferedImage img) {
        return img.getSubimage(img.getWidth() / 2, 0, 1, img.getHeight());
    }

    private BufferedImage extractMiddleTextureHorizontal(BufferedImage img) {
        return img.getSubimage(0, img.getHeight() / 2, img.getWidth(), 1);
    }
}
