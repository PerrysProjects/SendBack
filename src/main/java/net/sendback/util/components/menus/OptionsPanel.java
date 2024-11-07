package net.sendback.util.components.menus;

import net.sendback.util.Settings;
import net.sendback.util.components.Frame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel {
    private static OptionsPanel instance;
    private JSlider playerVolumeSlider;
    private JSlider backgroundVolumeSlider;
    private JSlider objectVolumeSlider;
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
        JLabel playerVolumeLabel = new JLabel("player Volume: ");
        add(playerVolumeLabel, c);

        playerVolumeSlider = new JSlider(0, 100, 50); // 50 als Standardwert
        playerVolumeSlider.setMajorTickSpacing(25);
        playerVolumeSlider.setPaintTicks(true);
        playerVolumeSlider.setPaintLabels(true);
        c.gridx = 1;
        add(playerVolumeSlider, c);
*/
        JLabel playerVolumeLabel = new JLabel("Player Volume:");
        add(playerVolumeLabel, c);

        playerVolumeSlider = new JSlider(0, 10, (int)(Settings.getFloat("volume.player") * 10)); // Lädt initialen Wert aus Settings
        playerVolumeSlider.setMajorTickSpacing(5);
        playerVolumeSlider.setPaintTicks(true);
        playerVolumeSlider.setPaintLabels(true);
        playerVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Settings.setFloat("Volume.player", playerVolumeSlider.getValue() / 10.0f); // Speichert Wert in Settings
            }
        });
        c.gridx = 1;
        add(playerVolumeSlider, c);


        c.gridx = 0;
        c.gridy++;
        JLabel backgroundVolumeLabel = new JLabel("Background Volume:");
        add(backgroundVolumeLabel, c);

        backgroundVolumeSlider = new JSlider(0, 10, (int)(Settings.getFloat("volume.music") * 10));
        backgroundVolumeSlider.setMajorTickSpacing(5);
        backgroundVolumeSlider.setPaintTicks(true);
        backgroundVolumeSlider.setPaintLabels(true);
        backgroundVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Settings.setFloat("Volume.music", backgroundVolumeSlider.getValue() / 10.0f);
            }
        });
        c.gridx = 1;
        add(backgroundVolumeSlider, c);


        // Objektlautstärke
        c.gridx = 0;
        c.gridy++;
        JLabel objectVolumeLabel = new JLabel("Object Volume:");
        add(objectVolumeLabel, c);

        objectVolumeSlider = new JSlider(0, 10, (int)(Settings.getFloat("volume.object") * 10));
        objectVolumeSlider.setMajorTickSpacing(5);
        objectVolumeSlider.setPaintTicks(true);
        objectVolumeSlider.setPaintLabels(true);
        objectVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Settings.setFloat("Volume.objects", objectVolumeSlider.getValue() / 10.0f);
            }
        });
        c.gridx = 1;
        add(objectVolumeSlider, c);


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
        JLabel devmodeCheckBox = new JLabel("Dev Mode:");
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
        JButton backButton = new JButton("Go Back");
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
        return playerVolumeSlider.getValue();
    }

    public int getbackgroundVolume() {
        return backgroundVolumeSlider.getValue();
    }

    public int getobjectVolume() {
        return objectVolumeSlider.getValue();
    }

    public boolean isFullscreen() {
        return fullscreenCheckBox.isSelected();
    }
}
