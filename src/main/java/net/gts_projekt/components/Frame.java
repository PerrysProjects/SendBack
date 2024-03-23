package net.gts_projekt.components;

import net.gts_projekt.Main;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        setTitle(Main.getName() + " | " + Main.getVersion());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        setContentPane(new GamePanel());
        getContentPane().setFocusable(true);
        getContentPane().requestFocusInWindow();
        pack();

        setSize(900, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
