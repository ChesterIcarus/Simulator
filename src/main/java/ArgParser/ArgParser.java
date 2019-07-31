package ArgParser;

import org.matsim.api.core.v01.network.Network;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.io.OsmNetworkReader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArgParser {
    private Config config;

    public Config getConfig() {
        return config;
    }

    /**
     * Parsing the command line arguments correctly.
     * Note: The order of processing arguments DOES matter. Changes w/o
     * consideration to those may result in unintended consequences.
     * @param commandLineArgs
     */
    public ArgParser(CommandLineArgs commandLineArgs){
        if (commandLineArgs.CONFIG != null)
            this.config = loadConfig(commandLineArgs.CONFIG);
        else
            this.config = ConfigUtils.createConfig();

        if (commandLineArgs.OSMNETWORK != null){
            if (commandLineArgs.NETWORK != null)
                loadOsmNetwork(commandLineArgs.OSMNETWORK,
                        commandLineArgs.NETWORK);
            else throw new IllegalArgumentException(
                    "Must pass path to write output of OSM conversion.");
        }
        else {
            if (commandLineArgs.NETWORK != null){
                this.config.network().setInputFile(commandLineArgs.NETWORK);
            }
        }

        if (commandLineArgs.PLANS != null) {
            this.config.plans().setInputFile(commandLineArgs.PLANS);
        }
        if (commandLineArgs.RUNID != null){
            String runId = commandLineArgs.RUNID + (new SimpleDateFormat(
                    "yyyy.MM.dd.HH.mm.ss").format(new Date()));
            this.config.controler().setRunId(runId);
        }
        if (commandLineArgs.OUTPUTDIR != null){
            this.config.controler().setOutputDirectory(commandLineArgs.OUTPUTDIR);
        }
    }

    private Config loadConfig(String file){
        return ConfigUtils.loadConfig(file);
    }

    private void loadOsmNetwork(String inFile, String outFile){
        CoordinateTransformation coordTransform = TransformationFactory.getCoordinateTransformation(
                "WGS84", this.config.global().getCoordinateSystem());
        Network network = NetworkUtils.createNetwork();
        OsmNetworkReader networkReader = new OsmNetworkReader(network, coordTransform);
        networkReader.parse(inFile);
        NetworkUtils.runNetworkSimplifier(network);
        NetworkUtils.runNetworkCleaner(network);
        NetworkUtils.writeNetwork(network, outFile);
        this.config.network().setInputFile(outFile);
    }
}
