package net.sendback.util;

import net.sendback.util.logging.LogType;
import net.sendback.util.logging.Logger;

import java.util.HashMap;

public class Settings {
    private static final HashMap<String, Object> settings = new HashMap<>();

    public static void init() {
        settings.put("screen.fps", 60);
        settings.put("screen.zoom", 14);
        settings.put("screen.dev", true);

        settings.put("keyBind.forward", 'w');
        settings.put("keyBind.left", 'a');
        settings.put("keyBind.backward", 's');
        settings.put("keyBind.right", 'd');

        settings.put("volume.music", 1.0F);
        settings.put("volume.player", 1.0F);

        settings.put("system.minMemory", 3072);
        settings.put("system.maxMemory", 4096);

        settings.put("system.opengl", true);
        settings.put("system.useSystemAAFontSettings", "lcd");
        settings.put("system.aatext", true);
        settings.put("system.d3d", true);
        settings.put("system.uiScale", 1);
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

    public static int getInt(String setting) {
        return getTypedSetting(setting, Integer.class);
    }

    public static void setInt(String setting, int value) {
        setSetting(setting, value);
    }

    public static double getDouble(String setting) {
        return getTypedSetting(setting, Double.class);
    }

    public static void setDouble(String setting, double value) {
        setSetting(setting, value);
    }

    public static float getFloat(String setting) {
        return getTypedSetting(setting, Float.class);
    }

    public static void setFloat(String setting, float value) {
        setSetting(setting, value);
    }
    public static boolean getBoolean(String setting) {
        return getTypedSetting(setting, Boolean.class);
    }

    public static void setBoolean(String setting, boolean value) {
        setSetting(setting, value);
    }

    public static String getString(String setting) {
        return getTypedSetting(setting, String.class);
    }

    public static void setString(String setting, String value) {
        setSetting(setting, value);
    }

    public static char getChar(String setting) {
        return getTypedSetting(setting, Character.class);
    }

    public static void setChar(String setting, char value) {
        setSetting(setting, value);
    }

    private static <T> T getTypedSetting(String setting, Class<T> type) {
        Object value = settings.get(setting);
        if(type.isInstance(value)) {
            return type.cast(value);
        } else {
            Logger.log("Setting '" + setting + "' is not of type " + type.getSimpleName(), LogType.WARN);
        }
        return null;
    }
}
