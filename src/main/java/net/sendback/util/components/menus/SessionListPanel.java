package net.sendback.util.components.menus;

import net.sendback.util.Session;

import net.sendback.util.components.SessionButton;
import net.sendback.util.components.TexturedButton;
import net.sendback.util.resources.ResourceGetter;
import net.sendback.worlds.generator.GeneratorPresets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SessionListPanel extends JPanel {
    private static SessionListPanel instance;

    private BufferedImage texture = ResourceGetter.getIconTexture("button.png");

    private static Session[] sessionList;
    private final TexturedButton newSeedButton;
    private final JPanel sessionListContainer;

    private SessionListPanel() {
        setBackground(Color.decode("#0A111B"));
        setFocusable(true);
        requestFocus();

        setLayout(new GridBagLayout());

        newSeedButton = createNewSeedButton();
        sessionListContainer = new JPanel(); // Container für die Session-Buttons
        sessionListContainer.setLayout(new GridBagLayout());
        sessionListContainer.setOpaque(false); // Setzt den Hintergrund auf transparent

        initializePanel();
    }

    // Initialisiert das Panel und fügt den `newSeedButton` und das `sessionListContainer` Panel hinzu
    private void initializePanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.CENTER;


        add(newSeedButton, c);


        c.gridy = 1;
        add(sessionListContainer, c);

        update();
    }

    private TexturedButton createNewSeedButton() {
        TexturedButton button = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "generate new Seed");
        button.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        button.setFont(ResourceGetter.getBold());
        button.setFontOffset(-10);
        button.setForeground(Color.decode("#4D6385"));
        button.setClickedForeground(Color.decode("#7790B5"));
        button.setPreferredSize(new Dimension(300, 100));
        button.addActionListener(e -> createNewSession());

        return button;
    }

    private void createNewSession() {
        String name = JOptionPane.showInputDialog(this, "Enter Session Name:");
        if (name == null || name.isEmpty()) return;

        String seedInput = JOptionPane.showInputDialog(this, "Enter Seed:");
        int seed;
        try {
            seed = Integer.parseInt(seedInput);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid seed value. Please enter an integer.");
            return;
        }

        Session newSession = new Session(name, seed, GeneratorPresets.FOREST);
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

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        for (Session session : sessionList) {
            sessionListContainer.add(new SessionButton(session, texture), c);
            c.gridy++;
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
