package net.sendback.util.components;

import net.sendback.Main;
import net.sendback.util.components.listener.MouseHandler;
import net.sendback.util.components.menus.GameCanvas;
import net.sendback.util.components.menus.MainMenuPanel;
import net.sendback.util.components.menus.SessionListPanel;
import net.sendback.util.resources.ResourceGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class Frame extends JFrame implements WindowStateListener {
    private static Frame instance;

    private Frame() {
        setTitle(Main.getName() + " | " + Main.getVersion());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        add(MainMenuPanel.getInstance());

        setIconImage(ResourceGetter.getIconTexture("clock.png"));

        setCursor(MouseHandler.getDefaultCursor());
        addMouseListener(new MouseHandler());

        setFocusable(true);
        requestFocus();
        revalidate();
        repaint();
        pack();

        addWindowStateListener(this);

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
        remove(SessionListPanel.getInstance());
        remove(GameCanvas.getInstance());

        add(component);
        component.setFocusable(true);
        component.requestFocus();
        component.revalidate();
        component.repaint();

        revalidate();
        repaint();
        pack();
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        if(GameCanvas.getInstance().getSession() != null) {
            if((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
                GameCanvas.getInstance().pause();
            } else if((e.getNewState() == Frame.NORMAL)) {
                GameCanvas.getInstance().resume();
            }
        }
    }
}
