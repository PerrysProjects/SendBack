package net.sendback.util.components;

import net.sendback.util.Session;
import net.sendback.util.components.menus.GameCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SessionButton extends JButton implements ActionListener {
    private final Session session;

    public SessionButton(Session session) {
        this.session = session;

        setSize(new Dimension(150, 100));
        setMinimumSize(new Dimension(150, 100));
        setMaximumSize(new Dimension(150, 100));
        setText(session.getName());

        // wird noch zu Round Button
/*
        RoundButton sessionButton = new RoundButton(session.getName());
        sessionButton.setFont(getFonts()[0]);
        setSize(new Dimension(150, 100));
        setMinimumSize(new Dimension(150, 100));
        setMaximumSize(new Dimension(150, 100));
        sessionButton.setPreferredSize(new Dimension(200, 200));
       */
        
        setFocusable(true);
        addActionListener(this);
        requestFocus();
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameCanvas.getInstance().setup(session);

        /*Frame.getInstance().getContentPane().removeAll();
        Frame.getInstance().add(GameCanvas.getInstance());
        Frame.getInstance().revalidate();
        Frame.getInstance().repaint();*/

        Frame.getInstance().switchPanel(GameCanvas.getInstance());
    }
}
