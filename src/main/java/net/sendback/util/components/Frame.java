package net.sendback.util.components;

import net.sendback.Main;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private static Frame instance;

    private Frame() {
        setTitle(Main.getName() + " | " + Main.getVersion());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setFocusable(true);

        //setContentPane(SessionListPanel.getInstance());
        //getContentPane().setFocusable(true);
        //getContentPane().requestFocus();
        //pack();

        add(SessionListPanel.getInstance());

        setFocusable(true);
        requestFocus();
        revalidate();
        repaint();
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

    public void switchPanel(Component component) {
        //remove(getContentPane());
        //setContentPane(component);
        //getContentPane().setFocusable(true);
        //getContentPane().requestFocus();
        //getContentPane().revalidate();
        //getContentPane().repaint();
        //pack();

        remove(SessionListPanel.getInstance());
        remove(GameCanvas.getInstance());

        add(component);

        setFocusable(true);
        requestFocus();
        revalidate();
        repaint();
        pack();
    }
}
