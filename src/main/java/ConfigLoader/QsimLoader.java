package ConfigLoader;

import org.matsim.core.config.Config;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.utils.misc.Time;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class QsimLoader {


    public QsimLoader(Config config, String file) throws InvocationTargetException, IllegalAccessException {
        Document document = XmlUtil.readFile(file);
        NodeList nodes = XmlUtil.getNodeList(document);

        for (int i = 0, len = nodes.getLength(); i < len; i++){
            Element element = (Element) nodes.item(i);
            for (Method method : QsimLoader.class.getDeclaredMethods()){
                if (method.getName().toLowerCase().contains(element.getTextContent().toLowerCase())){
                    method.invoke(null, config, element);
                }
            }
        }
    }

    /**
     * Parses the End Time from an XML element, in HH:MM:SS format.
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseEndTime(Config config, Element element){
        config.qsim().setEndTime(Time.parseTime(element.getTextContent()));
    }

    /**
     * Parses the Time Step Size in seconds from the XML element, in seconds.
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseTimeStepSize(Config config, Element element){
        config.qsim().setTimeStepSize(Double.parseDouble(element.getTextContent()));
    }

    /**
     * Parses the Snapshot Period from the XML element, in seconds.
     * Note: A Snapshot Period of 0 turns snapshots off
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseSnapshotPeriod(Config config, Element element){
        config.qsim().setSnapshotPeriod(Double.parseDouble(element.getTextContent()));
    }

    /**
     * Parses the Flow Capacity Factor from the XML element.
     * The result is applied to the config
     * @param config
     * @param element
     */
    private static void parseFlowCapacityFactor(Config config, Element element){
        config.qsim().setFlowCapFactor(Double.parseDouble(element.getTextContent()));
    }

    /**
     * Parses Stuck Time from XML element, in seconds
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseStuckTime(Config config, Element element){
        config.qsim().setStuckTime(Double.parseDouble(element.getTextContent()));

    }

    /**
     * Parses the boolean from the XML element, "true" being the only valid True
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseRemoveStuckVehicles(Config config, Element element){
        config.qsim().setRemoveStuckVehicles(Boolean.valueOf(element.getTextContent()));
    }

    /**
     * Parses the number of threads to use in the Qsim engine
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseNumberOfThreads(Config config, Element element){
        config.qsim().setNumberOfThreads(Integer.valueOf(element.getTextContent()));
    }

    /**
     * Parses the traffic dynamics from the XML element, and attempts to match against an existing Traffic Dynamic
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseTrafficDynamics(Config config, Element element){
        config.qsim().setTrafficDynamics(QSimConfigGroup.TrafficDynamics.valueOf(element.getTextContent()));
    }

    /**
     * Parses the main modes, which occur as children to the XML element mainModes, eg
     * <mainModes>
     *     <mode>car</mode>
     * </mainModes>
     * Applies the result to the config
     * @param config
     * @param element
     */
    private static void parseMainModes(Config config, Element element){
        NodeList childNodes = element.getChildNodes();
        String[] mainModes = new String[element.getChildNodes().getLength()];
        for (int i = 0; i < mainModes.length; i++){
            mainModes[i] = childNodes.item(i).getTextContent();
        }
        config.qsim().setMainModes(Arrays.asList(mainModes));
    }
}
