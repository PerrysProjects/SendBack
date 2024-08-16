package net.sendback.util.components;

import net.sendback.util.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private static MainMenuPanel instance;
    private final int buttonWidth;
    private final int buttonHeight;
    private Image backgroundImage;
    private int zoom = 1; // Initial zoom level

    private MainMenuPanel() {
        setLayout(new GridBagLayout());

        buttonWidth = 200;
        buttonHeight = 50;

        GridBagConstraints c = new GridBagConstraints();

        // Load background image
        backgroundImage = new ImageIcon("C:\\Users\\bader\\Downloads\\GTS_PRojekt\\src\\main\\resources\\assets\\textures\\tiles\\Menu\\Screenshot_2024-06-17_at_2.52.48_AM.png").getImage();

        RoundButton startGameButton = new RoundButton("Start Game");
        startGameButton.setFont(Resources.getFonts()[0]);
        startGameButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        startGameButton.setOpaque(false);
        startGameButton.updateUI();
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionListPanel sessionPanel = SessionListPanel.getInstance();
                sessionPanel.setBackgroundImage(backgroundImage); // Set background image to SessionListPanel
                sessionPanel.setZoom(zoom);  // Set zoom level to SessionListPanel
                Frame.getInstance().switchPanel(sessionPanel);
            }
        });
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 10, 0);
        add(startGameButton, c);

        RoundButton optionsButton = new RoundButton("Options");
        optionsButton.setFont(Resources.getFonts()[0]);
        optionsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        optionsButton.setOpaque(false);
        optionsButton.updateUI();
        optionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {System.exit(0);};
        });
        c.gridy = 1;
        add(optionsButton, c);

        RoundButton exitButton = new RoundButton("Exit");
        exitButton.setFont(Resources.getFonts()[0]);
        exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        exitButton.setOpaque(false);
        exitButton.updateUI();
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        c.gridy = 2;
        add(exitButton, c);
    }

    public static MainMenuPanel getInstance() {
        if(instance == null) {
            instance = new MainMenuPanel();
        }
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw background image with zoom level
        int width = getWidth() * zoom;
        int height = getHeight() * zoom;
        g2.drawImage(backgroundImage, 0, 0, width, height, this);
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
        revalidate();
        repaint();
    }
}
