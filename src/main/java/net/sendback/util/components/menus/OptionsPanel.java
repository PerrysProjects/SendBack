package net.sendback.util.components.menus;

import net.sendback.util.Settings;
import net.sendback.util.components.Frame;
import net.sendback.util.components.TexturedButton;
import net.sendback.util.components.TexturedCheckBox;
import net.sendback.util.components.TexturedSlider;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;



public class OptionsPanel extends JPanel {
    private static OptionsPanel instance;

    private final TexturedSlider musicVolumeSlider;
    private final JSlider playerVolumeSlider;
    private final JSlider entityVolumeSlider;
    private final JSlider objectVolumeSlider;

    private final TexturedCheckBox fullscreenCheckBox;
    private final TexturedCheckBox devOverlayCheckBox;

    private final TexturedButton goBackButton;
    private final TexturedButton resetButton;
    private final TexturedButton saveButton;

    //Test
    private Dimension previousWindowSize;
    private Point previousWindowLocation;

    private boolean isFullscreen;

    private OptionsPanel() {
        setBackground(Color.decode("#0A111B"));
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        JLabel titleLabel = new JLabel("Options", SwingConstants.CENTER);
        titleLabel.setFont(ResourceGetter.getBold().deriveFont(60f));
        titleLabel.setForeground(Color.decode("#4D6385"));
        add(titleLabel, c);

        c.gridy++;
        JPanel line = new JPanel();
        line.setBackground(Color.decode("#4D6385"));
        line.setPreferredSize(new Dimension(300, 4));
        add(line, c);

        c.gridy++;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        filler.setPreferredSize(new Dimension(0, 20));
        add(filler, c);

        c.anchor = GridBagConstraints.EAST;

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        JLabel musicVolumeLabel = new JLabel("Background Volume:");
        musicVolumeLabel.setFont(ResourceGetter.getBold());
        musicVolumeLabel.setForeground(Color.decode("#4D6385"));
        add(musicVolumeLabel, c);

        c.gridx = 2;
        musicVolumeSlider = new TexturedSlider(SwingConstants.HORIZONTAL, ResourceGetter.getIconTexture("track.png"), ResourceGetter.getIconTexture("thumb.png"));
        musicVolumeSlider.setMaximum(100);
        musicVolumeSlider.setValue((int) (Settings.getFloat("volume.music") * 100));
        musicVolumeSlider.setPreferredSize(new Dimension(250, 50));
        add(musicVolumeSlider, c);

        c.gridx = 0;
        c.gridy++;
        JLabel playerVolumeLabel = new JLabel("Player Volume:");
        playerVolumeLabel.setFont(ResourceGetter.getBold());
        playerVolumeLabel.setForeground(Color.decode("#4D6385"));
        add(playerVolumeLabel, c);

        c.gridx = 2;
        playerVolumeSlider = new TexturedSlider(SwingConstants.HORIZONTAL, ResourceGetter.getIconTexture("track.png"), ResourceGetter.getIconTexture("thumb.png"));
        playerVolumeSlider.setMaximum(100);
        playerVolumeSlider.setValue((int) (Settings.getFloat("volume.player") * 100));
        playerVolumeSlider.setPreferredSize(new Dimension(250, 50));
        add(playerVolumeSlider, c);

        c.gridx = 0;
        c.gridy++;
        JLabel entityVolumeLabel = new JLabel("Entity Volume:");
        entityVolumeLabel.setFont(ResourceGetter.getBold());
        entityVolumeLabel.setForeground(Color.decode("#4D6385"));
        add(entityVolumeLabel, c);

        c.gridx = 2;
        entityVolumeSlider = new TexturedSlider(SwingConstants.HORIZONTAL, ResourceGetter.getIconTexture("track.png"), ResourceGetter.getIconTexture("thumb.png"));
        entityVolumeSlider.setMaximum(100);
        entityVolumeSlider.setValue((int) (Settings.getFloat("volume.entity") * 100));
        entityVolumeSlider.setPreferredSize(new Dimension(250, 50));
        add(entityVolumeSlider, c);

        c.gridx = 0;
        c.gridy++;
        JLabel objectVolumeLabel = new JLabel("Object Volume:");
        objectVolumeLabel.setFont(ResourceGetter.getBold());
        objectVolumeLabel.setForeground(Color.decode("#4D6385"));
        add(objectVolumeLabel, c);

        c.gridx = 2;
        objectVolumeSlider = new TexturedSlider(SwingConstants.HORIZONTAL, ResourceGetter.getIconTexture("track.png"), ResourceGetter.getIconTexture("thumb.png"));
        objectVolumeSlider.setMaximum(100);
        objectVolumeSlider.setValue((int) (Settings.getFloat("volume.object") * 100));
        objectVolumeSlider.setPreferredSize(new Dimension(250, 50));
        add(objectVolumeSlider, c);

        c.gridx = 0;
        c.gridy++;
        JLabel fullscreenLabel = new JLabel("Fullscreen mode:");
        fullscreenLabel.setFont(ResourceGetter.getBold());
        fullscreenLabel.setForeground(Color.decode("#4D6385"));
        add(fullscreenLabel, c);

        c.gridx = 2;
        fullscreenCheckBox = new TexturedCheckBox(ResourceGetter.getIconTexture("button.png"));
        fullscreenCheckBox.setSelectedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        fullscreenCheckBox.setCheckmarkOffset(-2);
        fullscreenCheckBox.setForeground(Color.decode("#7790B5"));
        fullscreenCheckBox.setPreferredSize(new Dimension(50, 50));
        add(fullscreenCheckBox, c);

        c.gridx = 0;
        c.gridy++;
        JLabel devOverlayLabel = new JLabel("Dev Overlay:");
        devOverlayLabel.setFont(ResourceGetter.getBold());
        devOverlayLabel.setForeground(Color.decode("#4D6385"));
        add(devOverlayLabel, c);

        c.gridx = 2;
        devOverlayCheckBox = new TexturedCheckBox(ResourceGetter.getIconTexture("button.png"));
        devOverlayCheckBox.setSelectedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        devOverlayCheckBox.setCheckmarkOffset(-2);
        devOverlayCheckBox.setForeground(Color.decode("#7790B5"));
        devOverlayCheckBox.setPreferredSize(new Dimension(50, 50));
        devOverlayCheckBox.setSelected(Settings.getBoolean("screen.devOverlay"));
        add(devOverlayCheckBox, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 3;
        JPanel filler2 = new JPanel();
        filler2.setOpaque(false);
        filler2.setPreferredSize(new Dimension(0, 20));
        add(filler2, c);

        int buttonWidth = 150;
        int buttonHeight = 50;

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;

        goBackButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Go Back");
        goBackButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        goBackButton.setFont(ResourceGetter.getBold());
        goBackButton.setFontOffset(-6);
        goBackButton.setForeground(Color.decode("#4D6385"));
        goBackButton.setClickedForeground(Color.decode("#7790B5"));
        goBackButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        goBackButton.addActionListener(e -> Frame.getInstance().switchPanel(MainMenuPanel.getInstance()));
        add(goBackButton, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.CENTER;
        resetButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Reset");
        resetButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        resetButton.setFont(ResourceGetter.getBold());
        resetButton.setFontOffset(-6);
        resetButton.setForeground(Color.decode("#4D6385"));
        resetButton.setClickedForeground(Color.decode("#7790B5"));
        resetButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        resetButton.addActionListener(e -> {});
        add(resetButton, c);

        c.gridx = 2;
        c.anchor = GridBagConstraints.EAST;
        saveButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Save");
        saveButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        saveButton.setFont(ResourceGetter.getBold());
        saveButton.setFontOffset(-6);
        saveButton.setForeground(Color.decode("#4D6385"));
        saveButton.setClickedForeground(Color.decode("#7790B5"));
        saveButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        saveButton.addActionListener(e -> {
            Settings.setFloat("volume.music", (float) musicVolumeSlider.getValue() / 100);
            Settings.setFloat("volume.player", (float) playerVolumeSlider.getValue() / 100);
            Settings.setFloat("volume.entity", (float) entityVolumeSlider.getValue() / 100);
            Settings.setFloat("volume.object", (float) objectVolumeSlider.getValue() / 100);
            toggleFullscreen(fullscreenCheckBox.isSelected());
            //Settings.setBoolean("screen.fullscreen", fullscreenCheckBox.isSelected());
            Settings.setBoolean("screen.devOverlay", devOverlayCheckBox.isSelected());

            Settings.saveInFile();
        });
        add(saveButton, c);
    }

    public static OptionsPanel getInstance() {
        if(instance == null) {
            instance = new OptionsPanel();
        }
        return instance;
    }


      private void toggleFullscreen(boolean toggle) {

           if(Frame.getInstance().isUndecorated() != toggle) {
               Frame frame = Frame.getInstance();
               frame.dispose();
               //    frame.setLocationRelativeTo(null);
               frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());  // größe wird erfasst und gesetted aber wenn man es dann noch versucht zu zentrieren dann wird das frame nicht mehr gezeichnet
               frame.setUndecorated(toggle);

               frame.setVisible(true);

           }
       }



    public int getPlayerVolume() {
        return playerVolumeSlider.getValue();
    }
    public int getEntityVolume() {
        return entityVolumeSlider.getValue();
    }

    public int getBackgroundVolume() {
        return musicVolumeSlider.getValue();
    }

    public int getObjectVolume() {
        return objectVolumeSlider.getValue();
    }

    public boolean isFullscreen() {
        return fullscreenCheckBox.isSelected();
    }
}
