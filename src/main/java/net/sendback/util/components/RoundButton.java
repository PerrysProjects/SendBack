package net.sendback.util.components;

import net.sendback.util.components.listener.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    public RoundButton(String text) {
        super(text);
        setContentAreaFilled(false);
        addMouseListener(new MouseHandler());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Set up rounded clipping shape
        int arcWidth = 20;
        int arcHeight = 20;
        Shape roundRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        // Apply clipping to restrict the drawing area to the rounded rectangle shape
        g2d.setClip(roundRect);

        // Set color based on the button's state
        if(getModel().isArmed()) {
            g2d.setColor(Color.LIGHT_GRAY);
        } else {
            g2d.setColor(getBackground());
        }

        // Fill the rounded rectangle area
        g2d.fill(roundRect);

        // Call superclass paintComponent to ensure text and other elements are painted
        super.paintComponent(g2d);

        // Clean up
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw border around the rounded rectangle
        g2d.setColor(getForeground());
        int arcWidth = 20;
        int arcHeight = 20;
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));

        // Clean up
        g2d.dispose();
    }

    @Override
    public void setContentAreaFilled(boolean b) {
        // Overridden to prevent any background from being filled automatically
    }
}
