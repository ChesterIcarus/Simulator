package ArgParser;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CommandLineArgs {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-config",
            description = "Path to configuration file",
            required = false)
    public String CONFIG = null;

    @Parameter(names = "-global",
            description = "Global definition file for config generation")
    public String GLOBAL = null;

    @Parameter(names = "-plans",
    description = "Path to the plans file")
    public String PLANS = null;

    @Parameter(names = "-outputdir",
    description = "Sets the directory to write output to")
    public String OUTPUTDIR = null;



    @Parameter(names = "-runid",
    description = "ID for the run, note - this will be prepended to the time the simulation begins")
    public String RUNID = null;

    @Parameter(names = "-network",
            description = "Path to network input file",
            required = false)
    public String NETWORK = null;

    @Parameter(names = "-osm",
            description = "OSM file to be converted to MATsim network." +
                    "Note, must pass network path if passing OSM, where" +
                    "the network path is the output of OSM -> MATsim converison." +
                    "If network not passed exception thrown.",
            required = false)
    public String OSMNETWORK = null;

    @Parameter(names = "-qsim",
            description = "Qsim Definition file for config generation",
            required = false)
    public String QSIM = null;


}
