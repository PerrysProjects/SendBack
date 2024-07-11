package net.sendback.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Resources {
    private static Font[] fonts;
    private static HashMap<String, BufferedImage> tileTextures;
    private static HashMap<String, BufferedImage> entityTextures;

    private static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static GraphicsDevice device = env.getDefaultScreenDevice();
    private static GraphicsConfiguration config = device.getDefaultConfiguration();

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
        entityTextures = loadTextures("/assets/textures/entity");
    }

    private static HashMap<String, BufferedImage> loadTextures(String folderPath) {
        HashMap<String, BufferedImage> textures = new HashMap<>();

        try(InputStream folderStream = Resources.class.getResourceAsStream(folderPath)) {
            if(folderStream == null) {
                Logger.log("Resource folder " + folderPath + " not found.", LogType.ERROR);
                return textures;
            }

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(folderStream))) {
                String fileName;
                while((fileName = reader.readLine()) != null) {
                    try(InputStream fileStream = Resources.class.getResourceAsStream(folderPath + "/" + fileName)) {
                        if(fileStream != null) {
                            BufferedImage unconverted = ImageIO.read(fileStream);

                            BufferedImage converted = config.createCompatibleImage(unconverted.getWidth(), unconverted.getHeight(),
                                    Transparency.TRANSLUCENT);
                            Graphics g = converted.getGraphics();
                            g.drawImage(unconverted, 0, 0, unconverted.getWidth(), unconverted.getHeight(), null);
                            g.dispose();

                            textures.put(fileName, converted);
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
        return textures;
    }

    public static HashMap<String, BufferedImage> getTileTextures() {
        return tileTextures;
    }

    public static BufferedImage getTileTexture(String name) {
        return tileTextures.get(name);
    }

    public static HashMap<String, BufferedImage> getEntityTextures() {
        return entityTextures;
    }

    public static BufferedImage getEntityTexture(String name) {
        return entityTextures.get(name);
    }

    public static Font[] getFonts() {
        return fonts;
    }
}
