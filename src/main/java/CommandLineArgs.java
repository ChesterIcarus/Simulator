import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CommandLineArgs {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-config", description = "Path to configuration file", required = true)
    public String CONFIG = null;


    @Parameter(names = "-network", description = "Path to network input file")
    public String NETWORK = null;

    @Parameter(names = "-qsim",
            description = "Qsim Definition file for config generation")
    public String QSIM = null;
}
