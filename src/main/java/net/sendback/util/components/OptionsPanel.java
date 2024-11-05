package net.sendback.util.components;

import net.sendback.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel {
    private static OptionsPanel instance;
    private JSlider brightnessSlider;
    private JSlider gammaSlider;
    private JSlider volumeSlider;
    private JCheckBox fullscreenCheckBox;
    private JCheckBox devOverlay;

    private OptionsPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        // Helligkeit-Regler
        JLabel brightnessLabel = new JLabel("Brightness:");
        add(brightnessLabel, c);

        brightnessSlider = new JSlider(0, 100, 50); // 50 als Standardwert
        brightnessSlider.setMajorTickSpacing(25);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);
        c.gridx = 1;
        add(brightnessSlider, c);

        // Gamma-Regler
        c.gridx = 0;
        c.gridy++;
        JLabel gammaLabel = new JLabel("Gamma:");
        add(gammaLabel, c);

        gammaSlider = new JSlider(0, 100, 50);
        gammaSlider.setMajorTickSpacing(25);
        gammaSlider.setPaintTicks(true);
        gammaSlider.setPaintLabels(true);
        c.gridx = 1;
        add(gammaSlider, c);

        // Lautstärke-Regler
        c.gridx = 0;
        c.gridy++;
        JLabel volumeLabel = new JLabel("volume:");
        add(volumeLabel, c);

        volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        c.gridx = 1;
        add(volumeSlider, c);

        // Vollbild-Umschaltung
        c.gridx = 0;
        c.gridy++;
        JLabel fullscreenLabel = new JLabel("Full screen mode:");
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

        // Zurück-Button zum Hauptmenü
        c.gridx = 0;
        c.gridy++;
        JButton backButton = new JButton("go back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.getInstance().getContentPane().removeAll();
                Frame.getInstance().add(MainMenuPanel.getInstance());
                Frame.getInstance().revalidate();
                Frame.getInstance().repaint();
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
        Frame frame = Frame.getInstance();
        frame.dispose(); // Aktuellen Frame kurz schließen, um Änderungen anzuwenden
        frame.setUndecorated(isFullscreen); // Vollbild wenn true, sonst Fenstermodus
        frame.setVisible(true);
    }

    public int getBrightness() {
        return brightnessSlider.getValue();
    }

    public int getGamma() {
        return gammaSlider.getValue();
    }

    public int getVolume() {
        return volumeSlider.getValue();
    }

    public boolean isFullscreen() {
        return fullscreenCheckBox.isSelected();
    }
}
