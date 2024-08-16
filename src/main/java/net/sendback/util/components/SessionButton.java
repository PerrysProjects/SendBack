package net.sendback.util.components;

import net.sendback.util.Resources;
import net.sendback.util.Session;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SessionButton extends RoundButton implements ActionListener {
    private final Session session;

    public SessionButton(Session session) {
        super(session.getName());
        this.session = session;

        RoundButton sessionButton = new RoundButton(session.getName());
        sessionButton.setFont(Resources.getFonts()[0]);
        sessionButton.setPreferredSize(new Dimension(200, 50));

        setFocusable(true);
        addActionListener(this);
        requestFocus();
        setLayout(null);

        getClass().getResourceAsStream("/assets/font/textures/tiles/Menu/Screenshot_2024-06-17_at_2.52.48_AM.png");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameCanvas.getInstance().setup(session);
        Frame.getInstance().switchPanel(GameCanvas.getInstance());
    }
}
