package net.gts_projekt;

import net.gts_projekt.components.Frame;
import net.gts_projekt.util.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;

public class Main {
    private static String name;
    private static String version;
    private static Path path;

    public static Frame frame;

    public static void main(String[] args) {
        initialize();

        frame = new Frame();

        Session session = new Session("test");
        session.start();
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
        } catch(Exception e) {
            Logger.log(e);
        }
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