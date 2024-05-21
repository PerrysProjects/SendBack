package net.sendback.util;

import net.sendback.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Resources {
    private static String resourcePath;
    private static Font[] fonts;
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
        try {
            fonts = new Font[]{Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.PLAIN, 32F),
                    Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.BOLD, 32F),
                    Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.ITALIC, 32F)};
            Logger.log("Font loaded!");
        } catch(FontFormatException | IOException e) {
            Logger.log(e);
        }
        tileTextures = loadTextures("/assets/textures/tiles");
    }

    private static Map<String, Image> loadTextures(String folderPath) {
        Map<String, Image> textureFiles = new HashMap<>();

        try(InputStream folderStream = Resources.class.getResourceAsStream(folderPath)) {
            if(folderStream == null) {
                Logger.log("Resource folder " + folderPath + " not found.", LogType.ERROR);
                return textureFiles;
            }

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(folderStream))) {
                String fileName;
                while((fileName = reader.readLine()) != null) {
                    try(InputStream fileStream = Resources.class.getResourceAsStream(folderPath + "/" + fileName)) {
                        if(fileStream != null) {
                            textureFiles.put(fileName, ImageIO.read(fileStream));
                        } else {
                            Logger.log("Resource file " + folderPath + "/" + fileName + " not found.", LogType.WARN);
                        }
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch(IOException e) {
            Logger.log(e);
        }

        Logger.log("Resource folder " + folderPath + " loaded!");
        return textureFiles;
    }

    public static Map<String, Image> getTileTextures() {
        return tileTextures;
    }

    public static Image getTileTexture(String name) {
        return getTileTextures().get(name);
    }

    public static Font[] getFonts() {
        return fonts;
    }
}
