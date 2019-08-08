import ArgParser.ArgParser;
import ArgParser.CommandLineArgs;
import com.beust.jcommander.JCommander;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

public class Simulator {
    public static void main(String[] args){
        Simulator simulator = new Simulator();
        simulator.runSimulation(args);
    }

    public void runSimulation(String[] args){
        Config config = this.handleArgs(args);
        Scenario scenario = ScenarioUtils.loadScenario(config);
        Controler controler = new Controler(scenario);
        controler.run();
    }

    private Config handleArgs(String[] args){
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        JCommander.newBuilder().addObject(commandLineArgs).build().parse(args);
        ArgParser argParser = new ArgParser(commandLineArgs);
        return argParser.getConfig();
    }
}
