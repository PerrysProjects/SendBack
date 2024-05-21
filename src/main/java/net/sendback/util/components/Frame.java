package net.sendback.util.components;

import net.sendback.Main;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private static Frame instance;

    private Frame() {
        setTitle(Main.getName() + " | " + Main.getVersion());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        setContentPane(SessionListPanel.getInstance());
        getContentPane().setFocusable(true);
        getContentPane().requestFocus();
        pack();

        setSize(900, 700);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
    }

    public static Frame getInstance() {
        if(instance == null) {
            instance = new Frame();
        }
        return instance;
    }

    public void switchPanel(JPanel panel) {
        remove(getContentPane());
        setContentPane(panel);
        getContentPane().setFocusable(true);
        getContentPane().requestFocus();
        getContentPane().revalidate();
        getContentPane().repaint();
        pack();
    }
}
