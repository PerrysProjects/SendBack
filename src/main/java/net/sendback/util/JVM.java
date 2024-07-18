package net.sendback.util;

public class JVM {
    public static void init() {
        System.setProperty("sun.java2d.opengl", String.valueOf(Settings.getSetting("opengl")));
        System.setProperty("awt.useSystemAAFontSettings", String.valueOf(Settings.getSetting("useSystemAAFontSettings")));
        System.setProperty("swing.aatext", String.valueOf(Settings.getSetting("aatext")));
        System.setProperty("sun.java2d.d3d", String.valueOf(Settings.getSetting("d3d")));
        System.setProperty("sun.java2d.uiScale", String.valueOf(Settings.getSetting("uiScale")));
    }
}
