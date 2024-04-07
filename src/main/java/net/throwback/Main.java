package net.throwback;

import net.throwback.util.Logger;
import net.throwback.util.OperatingSystem;
import net.throwback.util.Session;
import net.throwback.util.components.Frame;
import net.throwback.util.components.SessionListPanel;
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

    public static void main(String[] args) {
        initialize();
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

        SessionListPanel.setSessionList(new Session[]{new Session("Test", -673232), new Session("ztt", 111), new Session("jhk", -65445)});

        Frame.getInstance().setVisible(true);
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
}