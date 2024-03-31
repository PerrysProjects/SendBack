package net.gts_projekt;

import net.gts_projekt.util.components.Frame;
import net.gts_projekt.util.Logger;
import net.gts_projekt.util.OperatingSystem;
import net.gts_projekt.util.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static String name;
    private static String version;
    private static Path path;
    private static OperatingSystem os;

    private static Frame frame;

    private static Session currentSession;

    public static void main(String[] args) {
        initialize();

        currentSession = new Session("test", -345676);
        currentSession.start();
    }

    private static void initialize() {
        name = "Name";
        version = "Version";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("pom.xml");

            NodeList projectNodes = doc.getElementsByTagName("project");
            if(projectNodes.getLength() > 0) {
                Node projectNode = projectNodes.item(0);
                NodeList children = projectNode.getChildNodes();
                for(int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if(child.getNodeName().equals("name")) {
                        name = child.getTextContent().trim();
                    } else if(child.getNodeName().equals("version")) {
                        version = child.getTextContent().trim();
                    }
                }
            }
        } catch(ParserConfigurationException | IOException | SAXException e) {
            Logger.log(e);
        }

        try {
            Path pathToJar = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            path = pathToJar.getParent();
        } catch (URISyntaxException e) {
            Logger.log(e);
        }

        os = OperatingSystem.checkOS(System.getProperty("os.name"));

        frame = new Frame();
    }

    public static String getName() {
        return name;
    }

    public static String getVersion() {
        return version;
    }

    public static Path getPath() {
        return path;
    }

    public static OperatingSystem getOs() {
        return os;
    }

    public static Frame getFrame() {
        return frame;
    }

    public static Session getCurrentSession() {
        return currentSession;
    }
}