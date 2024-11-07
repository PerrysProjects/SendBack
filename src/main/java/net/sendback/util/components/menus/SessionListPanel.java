package net.sendback.util.components.menus;

import net.sendback.util.Session;
import net.sendback.util.components.SessionButton;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;

public class SessionListPanel extends JPanel {
    private static SessionListPanel instance;
    private Image backgroundImage;

    private static Session[] sessionList;

    private SessionListPanel() {
        setBackground(Color.decode("#0A111B"));

        setFocusable(true);
        requestFocus();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.CENTER;

/*
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridBagLayout());

        int buttonWidth = 200;
        int buttonHeight = 50;

        GridBagConstraints c = new GridBagConstraints();
*/
        backgroundImage = ResourceGetter.getMenusTexture("menu.png");

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
