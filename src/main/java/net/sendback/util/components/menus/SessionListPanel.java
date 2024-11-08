package net.sendback.util.components.menus;

import net.sendback.util.Session;
import net.sendback.util.components.SessionButton;
import net.sendback.util.components.TexturedButton;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SessionListPanel extends JPanel {
    private static SessionListPanel instance;
    private Image backgroundImage;
    private BufferedImage texture = ResourceGetter.getIconTexture("button.png");;

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


       TexturedButton newSeedButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "generate new Seed");
        newSeedButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        newSeedButton.setFont(ResourceGetter.getBold());
        newSeedButton.setFontOffset(-10);
        newSeedButton.setForeground(Color.decode("#4D6385"));
        newSeedButton.setClickedForeground(Color.decode("#7790B5"));
        newSeedButton.setPreferredSize(new Dimension(300, 100));
        newSeedButton.addActionListener(e -> {

        });
        GridBagConstraints e = new GridBagConstraints();
        e.gridx = 0;
        e.gridy = 0;
        e.insets = new Insets(10, 10, 10, 10);
        e.fill = GridBagConstraints.LAST_LINE_END;
        add(newSeedButton, e);

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
            add(new SessionButton(session, texture));
        }
    }

    public static Session[] getSessionList() {
        return sessionList;
    }

    public static void setSessionList(Session[] sessionList) {
        SessionListPanel.sessionList = sessionList;
    }
}
