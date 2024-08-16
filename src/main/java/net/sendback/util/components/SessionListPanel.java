package net.sendback.util.components;

import net.sendback.util.Session;

import javax.swing.*;
import java.awt.*;

public class SessionListPanel extends JPanel {
    private static SessionListPanel instance;

    private int zoom = 1; // Initial zoom level
    private Image backgroundImage;
    private static Session[] sessionList;

    private SessionListPanel() {
        setFocusable(true);
        requestFocus();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public static SessionListPanel getInstance() {
        if(instance == null) {
            instance = new SessionListPanel();
        }
        instance.update();
        return instance;
    }

    private void update() {
        for(Session session : sessionList) {
            add(new SessionButton(session));
        }
    }
    public void setZoom(int zoom) {
        this.zoom = zoom;
        revalidate();
        repaint();
    }
    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2 = (Graphics2D) g;
            int width = getWidth() * zoom;
            int height = getHeight() * zoom;
            g2.drawImage(backgroundImage, 0, 0, width, height, this);
        }
    }

    public static Session[] getSessionList() {
        return sessionList;
    }

    public static void setSessionList(Session[] sessionList) {
        SessionListPanel.sessionList = sessionList;
    }
}
