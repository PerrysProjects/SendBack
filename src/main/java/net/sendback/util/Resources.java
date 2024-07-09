package net.sendback.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Resources {
    private static Font[] fonts;
    private static Map<String, Image> tileTextures = new HashMap<>();
    private static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static GraphicsDevice device = env.getDefaultScreenDevice();
    private static GraphicsConfiguration config = device.getDefaultConfiguration();

    public static BufferedImage img;

    public static void init() {
        try {
            fonts = new Font[]{Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.PLAIN, 32F),
                    Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.BOLD, 32F),
                    Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.ITALIC, 32F)};
            Logger.log("Font loaded!");
        } catch(FontFormatException | IOException e) {
            Logger.log(e);
        }
        loadTextures("/assets/textures/tiles");


    }

    private static void loadTextures(String folderPath) {
        try(InputStream folderStream = Resources.class.getResourceAsStream(folderPath)) {
            if(folderStream == null) {
                Logger.log("Resource folder " + folderPath + " not found.", LogType.ERROR);
                return;
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

                            tileTextures.put(fileName, converted);
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
