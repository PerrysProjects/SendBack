package net.gts_projekt;

import net.gts_projekt.components.Frame;
import net.gts_projekt.util.LogType;
import net.gts_projekt.util.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Main {
    public static String name;
    public static String version;

    public static Frame frame;

    public static void main(String[] args) {
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

       /* frame = new Frame();

        Session session = new Session("test");
        session.start(); */

        System.out.println("Hallo");
        Logger.log("Hallo2", LogType.ERROR);
    }
}