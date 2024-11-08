package net.sendback.util.components;

import net.sendback.util.Session;
import net.sendback.util.components.menus.GameCanvas;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SessionButton extends TexturedButton implements ActionListener {
    private final Session session;

    public SessionButton(Session session, BufferedImage texture) {
        super(texture);
        this.session = session;

        setText(session.getName());

        setClickedTexture(ResourceGetter.getIconTexture("clicked_button.png"));
        setFont(ResourceGetter.getBold());
        setFontOffset(-10);
        setForeground(Color.decode("#4D6385"));
        setClickedForeground(Color.decode("#7790B5"));
        setPreferredSize(new Dimension(150, 100));

        setFocusable(true);
        addActionListener(this);
        requestFocus();
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameCanvas.getInstance().setup(session);

        Frame.getInstance().switchPanel(GameCanvas.getInstance());
    }
}
