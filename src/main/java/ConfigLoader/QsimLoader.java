package ConfigLoader;

import org.matsim.core.config.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class QsimLoader {

    public QsimLoader(Config config, String file){
        Document document = XmlUtil.readFile(file);
        NodeList nodes = XmlUtil.getNodeList(document);

        for (int i = 0, len = nodes.getLength(); i < len; i++){
            Element element = (Element) nodes.item(i);
            switch (element.getNodeName()){
                case "startTime":

            }
        }
        config.qsim().
    }

    private void parseMainModes(Config config, Element element){

    }





}
