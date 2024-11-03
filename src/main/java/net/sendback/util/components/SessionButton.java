package net.sendback.util.components;

import net.sendback.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static net.sendback.util.resources.ResourceGetter.getFonts;

public class SessionButton extends JButton implements ActionListener {
    private final Session session;

    public SessionButton(Session session) {
        this.session = session;
/*
        setSize(new Dimension(150, 100));
        setMinimumSize(new Dimension(150, 100));
        setMaximumSize(new Dimension(150, 100));
        setText(session.getName());
*/
        RoundButton sessionButton = new RoundButton(session.getName());
        sessionButton.setFont(getFonts()[0]);
        setMinimumSize(new Dimension(150, 100));
        setMaximumSize(new Dimension(150, 100));
        sessionButton.setPreferredSize(new Dimension(200, 200));

        getClass().getResourceAsStream("/assets/font/textures/tiles/Menu/menu.png");

        setFocusable(true);
        addActionListener(this);
        requestFocus();
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameCanvas.getInstance().setup(session);
        //  Frame.getInstance().switchPanel(GameCanvas.getInstance());
        Frame.getInstance().getContentPane().removeAll();
        Frame.getInstance().add(GameCanvas.getInstance());
        Frame.getInstance().revalidate();
        Frame.getInstance().repaint();
    }
}
