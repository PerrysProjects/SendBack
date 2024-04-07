package net.throwback.util.components;

import net.throwback.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SessionButton extends JButton implements ActionListener {
    private Session session;

    public SessionButton(Session session) {
        this.session = session;

        setSize(new Dimension(150, 100));
        setMinimumSize(new Dimension(150, 100));
        setMaximumSize(new Dimension(150, 100));
        setText(session.getName());

        setFocusable(true);
        addActionListener(this);
        requestFocus();
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GamePanel.getInstance().setup(session);
        Frame.getInstance().switchPanel(GamePanel.getInstance());
    }
}
