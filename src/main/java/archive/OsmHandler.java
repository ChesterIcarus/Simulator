package archive;

import org.matsim.api.core.v01.Scenario;

import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.io.OsmNetworkReader;

public class OsmHandler {
    OsmHandler(){}

    void fromOsmFile(String osmFilename, Scenario scenario, String outputCoordSystem){
        CoordinateTransformation coordTransform = TransformationFactory.getCoordinateTransformation(
                "WGS84", outputCoordSystem);
        OsmNetworkReader networkReader = new OsmNetworkReader(scenario.getNetwork(), coordTransform);
        networkReader.parse(osmFilename);
    }

    void fromOsmFileSimple(String osmFilename, Scenario scenario, String outputCoordSystem){
        CoordinateTransformation coordTransform = TransformationFactory.getCoordinateTransformation(
                "WGS84", outputCoordSystem);
        OsmNetworkReader networkReader = new OsmNetworkReader(scenario.getNetwork(), coordTransform);
        networkReader.parse(osmFilename);
        NetworkUtils.runNetworkSimplifier(scenario.getNetwork());
        NetworkUtils.runNetworkCleaner(scenario.getNetwork());
    }

// void fromOsmFileVsp()

    void writeMatsimNetworkFile(String outputFile, Scenario scenario){
        NetworkWriter networkWriter = new NetworkWriter(scenario.getNetwork());
        networkWriter.write(outputFile);
    }
}
