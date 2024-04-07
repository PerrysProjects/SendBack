package net.throwback.util.components;

import net.throwback.util.Session;

import javax.swing.*;

public class SessionListPanel extends JPanel {
    private static SessionListPanel instance;

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

    public static Session[] getSessionList() {
        return sessionList;
    }

    public static void setSessionList(Session[] sessionList) {
        SessionListPanel.sessionList = sessionList;
    }
}
