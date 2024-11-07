package net.sendback.util.components.menus;


import net.sendback.util.components.Frame;
import net.sendback.util.components.RoundButton;
import net.sendback.util.components.TexturedButton;
import net.sendback.util.components.listener.MouseHandler;
import net.sendback.util.resources.ResourceGetter;

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

        buttonWidth = 400;
        buttonHeight = 100;

        GridBagConstraints c = new GridBagConstraints();

        // Load background image
        backgroundImage = ResourceGetter.getMenusTexture("menu.png");

        TexturedButton startGameButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Start Game");
        startGameButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        startGameButton.setFont(ResourceGetter.getBold());
        startGameButton.setFontOffset(-10);
        startGameButton.setForeground(Color.decode("#4D6385"));
        startGameButton.setClickedForeground(Color.decode("#7790B5"));
        startGameButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        startGameButton.addActionListener(e -> {
            Timer timer = new Timer(MouseHandler.getHoldTime(), event -> {
                Frame.getInstance().getContentPane().removeAll();
                Frame.getInstance().add(SessionListPanel.getInstance());
                Frame.getInstance().revalidate();
                Frame.getInstance().repaint();

                ((Timer) event.getSource()).stop();
            });

            timer.start();
        });
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 10, 0);
        add(startGameButton, c);

        RoundButton optionsButton = new RoundButton("Options");
        optionsButton.setFont(ResourceGetter.getBold());
        optionsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        optionsButton.setOpaque(false);
        optionsButton.updateUI();
        optionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                net.sendback.util.components.Frame.getInstance().getContentPane().removeAll();
                net.sendback.util.components.Frame.getInstance().add(OptionsPanel.getInstance());
                net.sendback.util.components.Frame.getInstance().revalidate();
                Frame.getInstance().repaint();
            }
        });
        c.gridy = 1;
        add(optionsButton, c);

        RoundButton exitButton = new RoundButton("Exit");
        exitButton.setFont(ResourceGetter.getBold());
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
