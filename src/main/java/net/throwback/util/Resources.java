package net.throwback.util;

import net.throwback.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Resources {
    private static String resourcePath;
    private static Map<String, Image> tileTextures;

    static {
        try {
            Path path = Paths.get(Objects.requireNonNull(Main.class.getResource("/")).toURI());
            resourcePath = path.toString();
        } catch(Exception e) {
            Logger.log(e);
        }
    }

    public static void init() {
        tileTextures = loadTextures("assets/textures/tiles");
    }

    private static Map<String, Image> loadTextures(String folderPath) {
        Map<String, Image> textureFiles = new HashMap<>();
        String resourcesPath = resourcePath;

        String fullPath = resourcesPath + File.separator + folderPath;
        File folder = new File(fullPath);

        if(!folder.exists()) {
            Logger.log(StringUtil.capitalize(folder.getName()) + " folder does not exist: " + fullPath, LogType.ERROR);
            return textureFiles;
        }

        File[] files = folder.listFiles();
        if(files != null) {
            for(File file : files) {
                if(file.isFile()) {
                    String fileName = file.getName().replaceFirst("[.][^.]+$", "");
                    try {
                        textureFiles.put(fileName, ImageIO.read(file));
                    } catch(IOException e) {
                        Logger.log(e);
                    }
                }
            }
        }

        Logger.log(StringUtil.capitalize(folder.getName()) + " folder loaded!");
        return textureFiles;
    }

    public static Map<String, Image> getTileTextures() {
        return tileTextures;
    }

    public static Image getTileTexture(String name) {
        return getTileTextures().get(name);
    }
}
