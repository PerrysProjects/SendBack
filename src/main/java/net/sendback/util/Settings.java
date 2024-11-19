package net.sendback.util;

import net.sendback.Main;
import net.sendback.util.files.FileReaderUtil;
import net.sendback.util.files.FileWriterUtil;
import net.sendback.util.logging.LogType;
import net.sendback.util.logging.Logger;

import java.util.Map;
import java.util.TreeMap;

public class Settings {
    private static final TreeMap<String, String> settings = new TreeMap<>();

    private static final FileWriterUtil settingsFile = new FileWriterUtil(Main.getPath().toString() + "/settings.txt");
    private static final FileReaderUtil settingsFileReader = new FileReaderUtil(Main.getPath().toString() + "/settings.txt");

    public static void init() {
        settings.put("screen.fps", "60");
        settings.put("screen.zoom", "14");
        settings.put("screen.devOverlay", "false");
        settings.put("screen.fullscreen", "false");

        settings.put("keyBind.forward", "w");
        settings.put("keyBind.left", "a");
        settings.put("keyBind.backward", "s");
        settings.put("keyBind.right", "d");
        settings.put("keyBind.inventory", "e");

        settings.put("volume.music", "1.0");
        settings.put("volume.player", "1.0");
        settings.put("volume.entity", "1.0");
        settings.put("volume.object", "1.0");

        settings.put("system.minMemory", "3072");
        settings.put("system.maxMemory", "4096");

        settings.put("system.opengl", "true");
        settings.put("system.useSystemAAFontSettings", "lcd");
        settings.put("system.aatext", "true");
        settings.put("system.d3d", "true");
        settings.put("system.uiScale", "1");

        for(String line : settingsFileReader.readAllLines()) {
            line = line.trim();

            if(line.contains("=")) {
                String[] parts = line.split("=", 2);
                String key = parts[0].trim();
                String value = parts[1].trim();

                settings.put(key, value);
            }
        }

        saveInFile();
    }

    public static void saveInFile() {
        settingsFile.clearFile();

        for(Map.Entry<String, String> entry : settings.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            settingsFile.appendLine(key + "=" + value);
        }
    }

    public static TreeMap<String, String> getSettings() {
        return settings;
    }

    public static String getSetting(String setting) {
        return settings.get(setting);
    }

    public static void setSetting(String setting, String value) {
        settings.replace(setting, value);
    }

    public static int getInt(String setting) {
        return getTypedSetting(setting, Integer.class);
    }

    public static void setInt(String setting, int value) {
        setSetting(setting, String.valueOf(value));
    }

    public static double getDouble(String setting) {
        return getTypedSetting(setting, Double.class);
    }

    public static void setDouble(String setting, double value) {
        setSetting(setting, String.valueOf(value));
    }

    public static float getFloat(String setting) {
        return getTypedSetting(setting, Float.class);
    }

    public static void setFloat(String setting, float value) {
        setSetting(setting, String.valueOf(value));
    }

    public static boolean getBoolean(String setting) {
        return getTypedSetting(setting, Boolean.class);
    }

    public static void setBoolean(String setting, boolean value) {
        setSetting(setting, String.valueOf(value));
    }

    public static char getChar(String setting) {
        return getTypedSetting(setting, Character.class);
    }

    public static void setChar(String setting, char value) {
        setSetting(setting, String.valueOf(value));
    }

    private static <T> T getTypedSetting(String setting, Class<T> type) {
        String value = settings.get(setting);

        if(value == null) {
            Logger.log("Setting '" + setting + "' is not found.", LogType.WARN);
            return null;
        }

        try {
            if(type == Integer.class) {
                return type.cast(Integer.parseInt(value));
            } else if(type == Double.class) {
                return type.cast(Double.parseDouble(value));
            } else if(type == Float.class) {
                return type.cast(Float.parseFloat(value));
            } else if(type == Boolean.class) {
                return type.cast(Boolean.parseBoolean(value));
            } else if(type == Character.class) {
                if(value.length() == 1) {
                    return type.cast(value.charAt(0));
                } else {
                    Logger.log("Setting '" + setting + "' cannot be converted to a single character.", LogType.WARN);
                    return null;
                }
            } else {
                Logger.log("Unsupported type conversion for setting '" + setting + "' to " + type.getSimpleName(), LogType.WARN);
                return null;
            }
        } catch(NumberFormatException e) {
            Logger.log("Failed to parse value '" + value + "' for setting '" + setting + "' as " + type.getSimpleName(), LogType.WARN);
            return null;
        }
    }
}
