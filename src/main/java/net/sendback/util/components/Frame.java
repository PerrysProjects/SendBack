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

    private boolean isFullscreen = false;
    private Dimension previousWindowSize;  // Speichert die Fenstergröße vor Vollbild
    private Point previousWindowLocation;  // Speichert die Fensterposition vor Vollbild
    private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();


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

    public void toggleFullscreen() {
        if (!isFullscreen) {
            // Speichert die aktuelle Fensterposition und -größe
            previousWindowSize = getSize();
            previousWindowLocation = getLocation();

            // Wechselt in den Vollbildmodus
            dispose();  // Fenster kurzzeitig unsichtbar machen, um Änderungen anzuwenden
            setUndecorated(true);  // Entfernt Rahmen und Titelleiste
            device.setFullScreenWindow(this);  // Setzt dieses Fenster als Vollbildfenster
            validate();
            isFullscreen = true;
        } else {
            // Beendet den Vollbildmodus und stellt die vorherige Fenstergröße wieder her
            device.setFullScreenWindow(null);  // Verlassen des Vollbildmodus
            setUndecorated(false);  // Rahmen und Titelleiste wieder anzeigen
            setSize(previousWindowSize);  // Ursprüngliche Größe wiederherstellen
            setLocation(previousWindowLocation);  // Ursprüngliche Position wiederherstellen
            validate();
            isFullscreen = false;
        }
        setVisible(true);  // Fenster sichtbar machen
    }
}
