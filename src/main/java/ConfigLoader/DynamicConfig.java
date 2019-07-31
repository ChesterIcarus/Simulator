package ConfigLoader;

import ArgParser.CommandLineArgs;
import com.beust.jcommander.JCommander;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

public class DynamicConfig {
    public DynamicConfig(String[] argv){
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        JCommander.newBuilder().addObject(commandLineArgs
                ).build().parse(argv);
        this.config = ConfigUtils.createConfig(commandLineArgs.CONFIG);
        commandLineArgs.getClass().getDeclaredFields();
    }
    public DynamicConfig(Config config){ this.config = config; }

    private List<String> activities = new ArrayList<>();

    private Config config;
    private String networkFile;
    private String inputCrs;
    private String planFile;

    public void setInputCrs(String inputCrs) { this.inputCrs = inputCrs; }
    public String getInputCrs(){ return this.inputCrs; }

    public void setPlanFile(String planFile) { this.planFile = planFile; }
    public String getPlanFile(){ return this.planFile; }

    public void setNetworkFile(String networkFile){ this.networkFile = networkFile; }
    public String getNetworkFile(){ return this.networkFile; }

    public void setConfig(Config config){ this.config = config; }
    public Config getConfig(){ return this.config; }

    public void setActivities(List<String> activities){ this.activities = activities; }
    public List<String> getActivities() { return activities; }




    public void loadQsimFile(String file){
    }

    /**
     * <networkChangeEvents>
     *
     *  <networkChangeEvent startTime="00:28:00">
     *      <link refId="1837925"/>
     *      <flowCapacity type="absolute" value="0"/>
     *      <freespeed type="absolute" value="0.0"/>
     *  </networkChangeEvent>
     * @param file
     */
    public void loadTimeVariant(String file){

    }

    public void writeConfig(String file){
        ConfigUtils.writeConfig(this.config, file);
    }
}
