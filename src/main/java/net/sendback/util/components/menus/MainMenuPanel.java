package net.sendback.util.components.menus;


import net.sendback.util.components.Frame;
import net.sendback.util.components.TexturedButton;
import net.sendback.util.components.listener.MouseHandler;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private static MainMenuPanel instance;
    private final int buttonWidth;
    private final int buttonHeight;
    private Image backgroundImage;
    private int zoom = 1;

    private MainMenuPanel() {
        setBackground(Color.decode("#0A111B"));
        setLayout(new GridBagLayout());

        buttonWidth = 250;
        buttonHeight = 80;

        GridBagConstraints c = new GridBagConstraints();

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
                Frame.getInstance().switchPanel(SessionListPanel.getInstance());

                ((Timer) event.getSource()).stop();
            });

            timer.start();
        });
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 10, 0);
        add(startGameButton, c);

        TexturedButton optionsButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Options");
        optionsButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        optionsButton.setFont(ResourceGetter.getBold());
        optionsButton.setFontOffset(-10);
        optionsButton.setForeground(Color.decode("#4D6385"));
        optionsButton.setClickedForeground(Color.decode("#7790B5"));
        optionsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        optionsButton.addActionListener(e -> Frame.getInstance().switchPanel(OptionsPanel.getInstance()));
        c.gridy = 1;
        add(optionsButton, c);

        TexturedButton exitButton = new TexturedButton(ResourceGetter.getIconTexture("button.png"), "Exit");
        exitButton.setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        exitButton.setFont(ResourceGetter.getBold());
        exitButton.setFontOffset(-10);
        exitButton.setForeground(Color.decode("#4D6385"));
        exitButton.setClickedForeground(Color.decode("#7790B5"));
        exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        exitButton.addActionListener(e -> System.exit(0));
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

        /*int width = getWidth() * zoom;
        int height = getHeight() * zoom;
        g2.drawImage(backgroundImage, 0, 0, width, height, this);*/
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
        revalidate();
        repaint();
    }
}
