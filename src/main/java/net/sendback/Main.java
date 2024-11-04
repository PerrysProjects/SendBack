package net.sendback;

import net.sendback.util.*;
import net.sendback.util.components.Frame;
import net.sendback.util.components.SessionListPanel;
import net.sendback.util.logging.LogType;
import net.sendback.util.logging.Logger;
import net.sendback.util.resources.ResourceGetter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static String name;
    private static String version;
    private static Path path;

    public static void main(String[] args) {
        initialize();
    }

    private static void initialize() {
        name = "Name";
        version = "Version";

        try {
            Path pathToJar = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            path = pathToJar.getParent();
        } catch (URISyntaxException e) {
            Logger.log(e);
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            InputStream pomIs = Main.class.getClassLoader().getResourceAsStream("META-INF/maven/net.sendback/SendBack/pom.xml");
            if (pomIs != null) {
                doc = builder.parse(pomIs);
            } else {
                doc = builder.parse("pom.xml");
            }


            NodeList projectNodes = doc.getElementsByTagName("project");
            if (projectNodes.getLength() > 0) {
                Node projectNode = projectNodes.item(0);
                NodeList children = projectNode.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeName().equals("name")) {
                        name = child.getTextContent().trim();
                    } else if (child.getNodeName().equals("version")) {
                        version = child.getTextContent().trim();
                    }
                }
            }

            Logger.log("Starting " + name + " version " + version + "!");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            Logger.log("Failed getting pom.xml, still staring game with error: " + Logger.buildStackTrace(e), LogType.WARN);
        }


        Settings.init();

        JVM.init();

        ResourceGetter.init();

        SessionListPanel.setSessionList(new Session[]{new Session("Test", -673232), new Session("ztt", 111), new Session("jhk", -65445)});

        SwingUtilities.invokeLater(() -> Frame.getInstance().setVisible(true));

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
}