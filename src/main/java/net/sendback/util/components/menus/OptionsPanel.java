package net.sendback.util.components.menus;

import net.sendback.util.Settings;
import net.sendback.util.components.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionsPanel extends JPanel {
    private static OptionsPanel instance;
    private JSlider playervolumeSlider;
    private JSlider backgroundvolumeSlider;
    private JSlider objectvolumeSlider;
    private JCheckBox fullscreenCheckBox;
    private JCheckBox devOverlay;

    private OptionsPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

/*
        JLabel playervolumeLabel = new JLabel("player volume: ");
        add(playervolumeLabel, c);

        playervolumeSlider = new JSlider(0, 100, 50); // 50 als Standardwert
        playervolumeSlider.setMajorTickSpacing(25);
        playervolumeSlider.setPaintTicks(true);
        playervolumeSlider.setPaintLabels(true);
        c.gridx = 1;
        add(playervolumeSlider, c);
*/
        JLabel playervolumeLabel = new JLabel("Player Volume:");
        add(playervolumeLabel, c);

        playervolumeSlider = new JSlider(0, 100, (int)(Settings.getFloat("volume.player") * 100)); // Lädt initialen Wert aus Settings
        playervolumeSlider.setMajorTickSpacing(25);
        playervolumeSlider.setPaintTicks(true);
        playervolumeSlider.setPaintLabels(true);
        playervolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Settings.setFloat("volume.player", playervolumeSlider.getValue() / 100.0f); // Speichert Wert in Settings
            }
        });
        c.gridx = 1;
        add(playervolumeSlider, c);


        c.gridx = 0;
        c.gridy++;
        JLabel backgroundvolumeLabel = new JLabel("Background Volume:");
        add(backgroundvolumeLabel, c);

        backgroundvolumeSlider = new JSlider(0, 100, (int)(Settings.getFloat("volume.music") * 100));
        backgroundvolumeSlider.setMajorTickSpacing(25);
        backgroundvolumeSlider.setPaintTicks(true);
        backgroundvolumeSlider.setPaintLabels(true);
        backgroundvolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Settings.setFloat("volume.music", backgroundvolumeSlider.getValue() / 100.0f);
            }
        });
        c.gridx = 1;
        add(backgroundvolumeSlider, c);


        // Objektlautstärke
        c.gridx = 0;
        c.gridy++;
        JLabel objectvolumeLabel = new JLabel("Object Volume:");
        add(objectvolumeLabel, c);

        objectvolumeSlider = new JSlider(0, 100, (int)(Settings.getFloat("volume.object") * 100)); 
        objectvolumeSlider.setMajorTickSpacing(25);
        objectvolumeSlider.setPaintTicks(true);
        objectvolumeSlider.setPaintLabels(true);
        objectvolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Settings.setFloat("volume.objects", objectvolumeSlider.getValue() / 100.0f);
            }
        });
        c.gridx = 1;
        add(objectvolumeSlider, c);


        // Vollbild-Umschaltung
        c.gridx = 0;
        c.gridy++;
        JLabel fullscreenLabel = new JLabel("Fullscreen mode:");
        add(fullscreenLabel, c);

        fullscreenCheckBox = new JCheckBox();
        fullscreenCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isFullscreen = fullscreenCheckBox.isSelected();
                toggleFullscreen(isFullscreen);
            }
        });
        c.gridx = 1;
        add(fullscreenCheckBox, c);

        c.gridx = 0;
        c.gridy++;
        JLabel devmodeCheckBox = new JLabel("dev mode:");
        add(devmodeCheckBox, c);

        devOverlay = new JCheckBox();
        devOverlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isDevOverlay = devOverlay.isSelected();
                Settings.setBoolean("screen.devOverlay", isDevOverlay);
            }
        });
        c.gridx = 1;
        add(devOverlay, c);

        // Zurück zum Hauptmenü
        c.gridx = 0;
        c.gridy++;
        JButton backButton = new JButton("GO BACK");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                net.sendback.util.components.Frame.getInstance().getContentPane().removeAll();
                net.sendback.util.components.Frame.getInstance().add(MainMenuPanel.getInstance());
                net.sendback.util.components.Frame.getInstance().revalidate();
                net.sendback.util.components.Frame.getInstance().repaint();
            }
        });
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy++;
        add(backButton, c);
    }

    public static OptionsPanel getInstance() {
        if (instance == null) {
            instance = new OptionsPanel();
        }
        return instance;
    }

    private void toggleFullscreen(boolean isFullscreen) {
        net.sendback.util.components.Frame frame = Frame.getInstance();
        frame.dispose(); // Aktuellen Frame kurz schließen, um Änderungen anzuwenden
        frame.setUndecorated(isFullscreen); // Vollbild wenn true, sonst Fenstermodus
        frame.setVisible(true);
    }

    public int getplayerVolume() {
        return playervolumeSlider.getValue();
    }

    public int getbackgroundVolume() {
        return backgroundvolumeSlider.getValue();
    }

    public int getobjectVolume() {
        return objectvolumeSlider.getValue();
    }

    public boolean isFullscreen() {
        return fullscreenCheckBox.isSelected();
    }
}
