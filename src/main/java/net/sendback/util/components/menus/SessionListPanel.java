package net.sendback.util.components.menus;

import net.sendback.util.Session;
import net.sendback.util.components.Frame;
import net.sendback.util.components.SessionButton;
import net.sendback.util.components.TexturedButton;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SessionListPanel extends JPanel {
    private static SessionListPanel instance;

    private BufferedImage texture = ResourceGetter.getIconTexture("button.png");

    private static Session[] sessionList;
    private final TexturedButton newSeedButton;
    private final TexturedButton goBackButton;
    private final JPanel sessionListContainer;

    private SessionListPanel() {
        setBackground(Color.decode("#0A111B"));
        setFocusable(true);
        requestFocus();
/*
        setLayout(new GridBagLayout());

        newSeedButton = createNewSeedButton();
        goBackButton = createGoBackButton();
        sessionListContainer = new JPanel(); // Container für die Session-Buttons
        sessionListContainer.setLayout(new GridBagLayout());
        sessionListContainer.setOpaque(false); // Setzt den Hintergrund auf transparent
*/

        setLayout(new BorderLayout());

        newSeedButton = createNewSeedButton();
        goBackButton = createGoBackButton();

        // Session-Container erstellen
        sessionListContainer = new JPanel();
        sessionListContainer.setLayout(new GridLayout(3, 3, 20, 20));
        sessionListContainer.setOpaque(false);


        initializePanel();
    }

    // Initialisiert das Panel und fügt den `newSeedButton', den goBackButton und das `sessionListContainer` Panel hinzu
    private void initializePanel() {
        /*
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.CENTER;

        add(newSeedButton, c);
        add(goBackButton, c);

        c.gridy = 1;
        add(sessionListContainer, c);
*/

        // Session-Container mittig platzieren
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(sessionListContainer);

        // Buttons unten anordnen
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(newSeedButton);
        bottomPanel.add(goBackButton);


        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        update();
    }

    private TexturedButton createNewSeedButton() {
        TexturedButton button = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Generate Seed");
        button.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        button.setFont(ResourceGetter.getBold());
        button.setFontOffset(-10);
        button.setForeground(Color.decode("#4D6385"));
        button.setClickedForeground(Color.decode("#7790B5"));
        button.setPreferredSize(new Dimension(200, 100));
        button.addActionListener(e -> createNewSession());

        return button;
    }

    private TexturedButton createGoBackButton() {
        TexturedButton goBackButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Go Back");
        goBackButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        goBackButton.setFont(ResourceGetter.getBold());
        goBackButton.setFontOffset(-6);
        goBackButton.setForeground(Color.decode("#4D6385"));
        goBackButton.setClickedForeground(Color.decode("#7790B5"));
        goBackButton.setPreferredSize(new Dimension(200, 100));
        goBackButton.addActionListener(e -> Frame.getInstance().switchPanel(MainMenuPanel.getInstance()));

        return goBackButton;

    }

    private void createNewSession() {
        String name = JOptionPane.showInputDialog(this, "Enter Session Name:");
        if (name == null || name.isEmpty()) return;

        String seedInput = JOptionPane.showInputDialog(this, "Enter Seed:");
        int seed;
        try {
            seed = Integer.parseInt(seedInput);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid seed value. Please enter only numbers");
            return;
        }

        Session newSession = new Session(name, seed);
        addSession(newSession);
    }

    private void addSession(Session newSession) {
        Session[] updatedSessionList = new Session[sessionList.length + 1];
        System.arraycopy(sessionList, 0, updatedSessionList, 0, sessionList.length);
        updatedSessionList[sessionList.length] = newSession;
        sessionList = updatedSessionList;

        update();
    }

    public static SessionListPanel getInstance() {
        if (instance == null) {
            instance = new SessionListPanel();
        }
        instance.update();
        return instance;
    }

    private void update() {
        sessionListContainer.removeAll();
/*
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
*/
        for (Session session : sessionList) {
            sessionListContainer.add(new SessionButton(session, texture)); //, c
   //         c.gridy++;
        }

        sessionListContainer.revalidate();
        sessionListContainer.repaint();
    }

    public static Session[] getSessionList() {
        return sessionList;
    }

    public static void setSessionList(Session[] sessionList) {
        SessionListPanel.sessionList = sessionList;
    }
}
