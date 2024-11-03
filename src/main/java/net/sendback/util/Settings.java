package net.sendback.util;

import java.util.HashMap;

public class Settings {
    private static final HashMap<String, Object> settings = new HashMap<>();

    public static void init() {
        settings.put("zoom", 14);

        settings.put("forward", 'w');
        settings.put("left", 'a');
        settings.put("backward", 's');
        settings.put("right", 'd');

        settings.put("volume", 1.0);

        settings.put("minMemory", 3072);
        settings.put("maxMemory", 4096);

        settings.put("opengl", true);
        settings.put("useSystemAAFontSettings", "lcd");
        settings.put("aatext", true);
        settings.put("d3d", true);
        settings.put("uiScale", 1);
    }

    public static HashMap<String, Object> getSettings() {
        return settings;
    }

    public static Object getSetting(String setting) {
        return settings.get(setting);
    }

    public static Object setSetting(String setting, Object value) {
        return settings.put(setting, value);
    }
}
