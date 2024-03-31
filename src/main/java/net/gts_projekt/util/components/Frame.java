package net.gts_projekt.util.components;

import net.gts_projekt.Main;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        setTitle(Main.getName() + " | " + Main.getVersion());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        setContentPane(new GamePanel());
        getContentPane().setFocusable(true);
        getContentPane().requestFocus();
        pack();

        setSize(900, 700);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
