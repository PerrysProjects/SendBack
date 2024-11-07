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
    private static Font plain;
    private static Font bold;
    private static Font italic;

    private static HashMap<String, BufferedImage> tileTextures;
    private static HashMap<String, BufferedImage> playerTextures;
    private static HashMap<String, BufferedImage> iconTextures;
    private static HashMap<String, BufferedImage> menusTextures;

    private static HashMap<String, Clip> backgroundMusic;
    private static HashMap<String, Clip> entityWalkSounds;

    private static final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final GraphicsDevice device = env.getDefaultScreenDevice();
    private static final GraphicsConfiguration config = device.getDefaultConfiguration();

    public static void init() {
        try {
            plain = Font.createFont(Font.TRUETYPE_FONT, ResourceGetter.class.getClassLoader().getResourceAsStream("assets/font/MaruMonica.ttf")).deriveFont(Font.PLAIN, 32F);
            bold = Font.createFont(Font.TRUETYPE_FONT, ResourceGetter.class.getClassLoader().getResourceAsStream("assets/font/MaruMonica.ttf")).deriveFont(Font.BOLD, 32F);
            italic = Font.createFont(Font.TRUETYPE_FONT, ResourceGetter.class.getClassLoader().getResourceAsStream("assets/font/MaruMonica.ttf")).deriveFont(Font.ITALIC, 32F);
            Logger.log("Font loaded");
        } catch(FontFormatException | IOException e) {
            Logger.log(e);
        }

        tileTextures = loadTextures("assets/textures/tiles");
        playerTextures = loadTextures("assets/textures/entity/player");
        iconTextures = loadTextures("assets/textures/icons");
        menusTextures = loadTextures("assets/textures/menus");

        backgroundMusic = loadClips("assets/sounds/music/background");
        entityWalkSounds = loadClips("assets/sounds/entity/walk");
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

        AudioFormat baseFormat = audioInputStream.getFormat();
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
        );

        // Decode the stream if the format is unsupported
        AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
        Clip clip = AudioSystem.getClip();
        clip.open(decodedAudioInputStream);

        return clip;
    }

    public static Font getPlain() {
        return plain;
    }

    public static Font getBold() {
        return bold;
    }

    public static Font getItalic() {
        return italic;
    }

    public static HashMap<String, BufferedImage> getTileTextures() {
        return tileTextures;
    }

    public static BufferedImage getTileTexture(String name) {
        return tileTextures.get(name);
    }

    public static HashMap<String, BufferedImage> getPlayerTextures() {
        return playerTextures;
    }

    public static BufferedImage getPlayerTexture(String name) {
        return playerTextures.get(name);
    }

    public static HashMap<String, BufferedImage> getIconTextures() {
        return iconTextures;
    }

    public static BufferedImage getIconTexture(String name) {
        return iconTextures.get(name);
    }
    public static HashMap<String, BufferedImage> getMenusTextures() {
        return menusTextures;
    }

    public static BufferedImage getMenusTexture(String name) {
        return menusTextures.get(name);
    }

    public static HashMap<String, Clip> getBackgroundMusic() {
        return backgroundMusic;
    }

    public static Clip getBackgroundMusic(String name) {
        return backgroundMusic.get(name);
    }

    public static HashMap<String, Clip> getEntityWalkSounds() {
        return entityWalkSounds;
    }

    public static Clip getEntityWalkSound(String name) {
        return entityWalkSounds.get(name);
    }
}
