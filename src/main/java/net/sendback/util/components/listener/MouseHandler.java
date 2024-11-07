package net.sendback.util.components.listener;

import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private static Cursor defaultCursor;
    private static Cursor clickCursor;
    private static Cursor holdCursor;

    private static Timer holdTimer;
    private static final int holdTime = 150;

    public static void init() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotspot = new Point(0, 0);

        defaultCursor = toolkit.createCustomCursor(ResourceGetter.getIconTexture("cursor.png"), hotspot, "Default Cursor");
        clickCursor = toolkit.createCustomCursor(ResourceGetter.getIconTexture("click_cursor.png"), hotspot, "Click Cursor");
        holdCursor = toolkit.createCustomCursor(ResourceGetter.getIconTexture("hold_cursor.png"), hotspot, "Hold Cursor");
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        holdTimer = new Timer(holdTime, event -> {
            e.getComponent().setCursor(holdCursor);
        });
        holdTimer.setRepeats(false);
        holdTimer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(holdTimer.isRunning()) {
            holdTimer.stop();
            e.getComponent().setCursor(clickCursor);
        }

        Timer resetCursorTimer = new Timer(100, event -> e.getComponent().setCursor(defaultCursor));
        resetCursorTimer.setRepeats(false);
        resetCursorTimer.start();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static Cursor getDefaultCursor() {
        return defaultCursor;
    }

    public static Cursor getClickCursor() {
        return clickCursor;
    }

    public static Cursor getHoldCursor() {
        return holdCursor;
    }

    public static int getHoldTime() {
        return holdTime;
    }
}
