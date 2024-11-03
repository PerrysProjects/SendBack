package net.sendback.util.components;

import net.sendback.util.Session;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SessionListPanel extends JPanel {
    private static SessionListPanel instance;

    private static Session[] sessionList;

    private SessionListPanel() {

        setFocusable(true);
        requestFocus();

        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridBagLayout());

        int buttonWidth = 200;
        int buttonHeight = 50;

        GridBagConstraints c = new GridBagConstraints();


        BufferedImage backgroundImage = ResourceGetter.getMenusTexture("menu.png");
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

    public static Session[] getSessionList() {
        return sessionList;
    }

    public static void setSessionList(Session[] sessionList) {
        SessionListPanel.sessionList = sessionList;
    }
}
