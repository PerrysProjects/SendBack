package net.sendback.util;

public class JVM {
    public static void init() {
        System.setProperty("sun.java2d.opengl", String.valueOf(Settings.getBoolean("system.opengl")));
        System.setProperty("awt.useSystemAAFontSettings", Settings.getSetting("system.useSystemAAFontSettings"));
        System.setProperty("swing.aatext", String.valueOf(Settings.getBoolean("system.aatext")));
        System.setProperty("sun.java2d.d3d", String.valueOf(Settings.getBoolean("system.d3d")));
        System.setProperty("sun.java2d.uiScale", String.valueOf(Settings.getInt("system.uiScale")));
    }
}
