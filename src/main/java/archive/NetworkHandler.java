package archive;

import archive.OsmHandler;
import org.matsim.api.core.v01.Scenario;

public class NetworkHandler {
    public NetworkHandler(){}

    public static void readOsmWriteMatsim(String osmFile, String coordSystem, Scenario scenario) {
        OsmHandler osmHandler = new OsmHandler();
        String outputFile = osmFile.split("\\.osm")[0] + "_fromOsm.xml";
        osmHandler.fromOsmFileSimple(osmFile, scenario, coordSystem);
        osmHandler.writeMatsimNetworkFile(outputFile, scenario);
    }

    public static void readOsmWriteMatsim(String osmFile, String outputFile, String coordSystem, Scenario scenario) {
        OsmHandler osmHandler = new OsmHandler();
        osmHandler.fromOsmFileSimple(osmFile, scenario, coordSystem);
        osmHandler.writeMatsimNetworkFile(outputFile, scenario);
    }
}
