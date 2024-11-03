package net.sendback.util.resources;

import net.sendback.util.logging.LogType;
import net.sendback.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceGetter {
    private static Font[] fonts;
    private static HashMap<String, BufferedImage> tileTextures;
    private static HashMap<String, BufferedImage> entityTextures;
    private static HashMap<String, BufferedImage> iconTextures;

    private static HashMap<String, Clip> backgroundMusic;

    private static final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final GraphicsDevice device = env.getDefaultScreenDevice();
    private static final GraphicsConfiguration config = device.getDefaultConfiguration();

    public static void init() {
        try {
            fonts = new Font[]{Font.createFont(Font.TRUETYPE_FONT, ResourceGetter.class.getClassLoader().getResourceAsStream("assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.PLAIN, 32F),
                    Font.createFont(Font.TRUETYPE_FONT, ResourceGetter.class.getClassLoader().getResourceAsStream("assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.BOLD, 32F),
                    Font.createFont(Font.TRUETYPE_FONT, ResourceGetter.class.getClassLoader().getResourceAsStream("assets/font/x12y16pxMaruMonica.ttf")).deriveFont(Font.ITALIC, 32F)};
            Logger.log("Font loaded");
        } catch(FontFormatException | IOException e) {
            Logger.log(e);
        }

        tileTextures = loadTextures("assets/textures/tiles");
        entityTextures = loadTextures("assets/textures/entity");
        iconTextures = loadTextures("assets/textures/icons");

        backgroundMusic = loadClips("assets/sound/backgroundMusic");
    }

    private static HashMap<String, BufferedImage> loadTextures(String folderPath) {
        HashMap<String, BufferedImage> textures = new HashMap<>();
        URL url = ResourceGetter.class.getClassLoader().getResource(folderPath);

        if(url == null) {
            Logger.log("Resource folder " + folderPath + " not found", LogType.ERROR);
            return textures;
        }

        try {
            if(url.getProtocol().equals("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                try(JarFile jarFile = jarURLConnection.getJarFile()) {
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while(entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if(name.startsWith(folderPath) && !entry.isDirectory() && name.endsWith(".png")) {
                            try(InputStream fileStream = ResourceGetter.class.getClassLoader().getResourceAsStream(name)) {
                                if(fileStream != null) {
                                    BufferedImage unconverted = ImageIO.read(fileStream);
                                    BufferedImage converted = config.createCompatibleImage(unconverted.getWidth(), unconverted.getHeight(), Transparency.TRANSLUCENT);
                                    Graphics g = converted.getGraphics();
                                    g.drawImage(unconverted, 0, 0, unconverted.getWidth(), unconverted.getHeight(), null);
                                    g.dispose();
                                    textures.put(name.substring(folderPath.length() + 1), converted);
                                    Logger.log("Resource file " + name + " loaded");
                                } else {
                                    Logger.log("Resource file " + name + " not found", LogType.WARN);
                                }
                            } catch(IOException e) {
                                Logger.log(e);
                            }
                        }
                    }
                }
            } else {
                File folder = new File(url.toURI());
                for(File file : folder.listFiles()) {
                    if(file.isFile() && file.getName().endsWith(".png")) {
                        try(InputStream fileStream = new FileInputStream(file)) {
                            BufferedImage unconverted = ImageIO.read(fileStream);
                            BufferedImage converted = config.createCompatibleImage(unconverted.getWidth(), unconverted.getHeight(), Transparency.TRANSLUCENT);
                            Graphics g = converted.getGraphics();
                            g.drawImage(unconverted, 0, 0, unconverted.getWidth(), unconverted.getHeight(), null);
                            g.dispose();
                            textures.put(file.getName(), converted);
                            Logger.log("Resource file " + file.getName() + " loaded");
                        } catch(IOException e) {
                            Logger.log(e);
                        }
                    }
                }
            }
        } catch(Exception e) {
            Logger.log(e);
        }

        Logger.log("Resource folder " + folderPath + " loaded");
        return textures;
    }

    public static HashMap<String, Clip> loadClips(String folderPath) {
        HashMap<String, Clip> clips = new HashMap<>();
        URL url = ResourceGetter.class.getClassLoader().getResource(folderPath);

        if(url == null) {
            Logger.log("Resource folder " + folderPath + " not found", LogType.ERROR);
            return clips;
        }

        try {
            if(url.getProtocol().equals("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                try(JarFile jarFile = jarURLConnection.getJarFile()) {
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while(entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if(name.startsWith(folderPath) && !entry.isDirectory() && name.endsWith(".wav")) {
                            try(InputStream fileStream = ResourceGetter.class.getClassLoader().getResourceAsStream(name)) {
                                if(fileStream != null) {
                                    Clip clip = loadClipFromStream(fileStream);
                                    clips.put(name.substring(folderPath.length() + 1), clip);
                                    Logger.log("Resource file " + name + " loaded");
                                } else {
                                    Logger.log("Resource file " + name + " not found", LogType.WARN);
                                }
                            } catch(IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                                Logger.log(e);
                            }
                        }
                    }
                }
            } else {
                File folder = new File(url.toURI());
                for(File file : folder.listFiles()) {
                    if(file.isFile() && file.getName().endsWith(".wav")) {
                        try(InputStream fileStream = new FileInputStream(file)) {
                            Clip clip = loadClipFromStream(fileStream);
                            clips.put(file.getName(), clip);
                            Logger.log("Resource file " + file.getName() + " loaded");
                        } catch(IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                            Logger.log(e);
                        }
                    }
                }
            }
        } catch(Exception e) {
            Logger.log(e);
        }

        Logger.log("Resource folder " + folderPath + " loaded");
        return clips;
    }

    private static Clip loadClipFromStream(InputStream stream) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        BufferedInputStream bufferedStream = new BufferedInputStream(stream);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
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

    public static HashMap<String, BufferedImage> getIconTextures() {
        return iconTextures;
    }

    public static BufferedImage getIconTexture(String name) {
        return iconTextures.get(name);
    }

    public static HashMap<String, Clip> getBackgroundMusic() {
        return backgroundMusic;
    }

    public static Clip getBackgroundMusic(String name) {
        return backgroundMusic.get(name);
    }

    public static Font[] getFonts() {
        return fonts;
    }
}
