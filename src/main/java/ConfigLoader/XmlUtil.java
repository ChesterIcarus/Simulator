package ConfigLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

class XmlUtil {
    private XmlUtil(){}

    public static Document readFile(final String file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(file));
            document.getDocumentElement().normalize();
            return document;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static NodeList getNodeList(final Document document) {
        Element docEle = document.getDocumentElement();
        return docEle.getChildNodes();
    }


    public static boolean hasChildren(final Node node){
        NodeList nodeList = node.getChildNodes();
        if (nodeList.getLength() > 0){
            return true;
        }
        return false;
    }
}
