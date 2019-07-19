package archive;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.IOException;

public class Simulate {
    public static void main(String[] args) throws IOException {
        if (args[0].equals("convert_osm_and_config")) {
            if (args.length == 6)
                Simulate.preprocessMATsimInput(args[1], args[2], args[3], args[4], args[5]);
            else
                throw new IllegalArgumentException("Check command line input conforms to requirements");
        }
        if (args[0].equals("run_sim")) {
            Simulate.runMATsimFromConfig(args[1]);
        }
        if (args[0].equals("make_config")) {
//            archive.Simulate.(args[1], args[2], args[3], args[4]);
        }
        if (args[0].equals("convert_osm")) {
            Simulate.createMatsimNetworkFromOsm(args[1], args[2]);
        }
    }

    public static void preprocessMATsimInput(String osmFile, String configInFile,
                                             String configOutFile, String planFile, String networkFile) {
        String coordSystem = "EPSG:2223";
        DynamicConfig configHandler = new DynamicConfig();
        configHandler.setInputCrs(coordSystem);
        configHandler.setPlanFile(planFile);
        configHandler.setNetworkFile(networkFile);
        configHandler.writeConfig(configOutFile);
        Scenario scenario = ScenarioUtils.loadScenario(ConfigUtils.createConfig());
        NetworkHandler.readOsmWriteMatsim(osmFile, networkFile, coordSystem, scenario);
    }

    public static void createMatsimNetworkFromOsm(String osmFile, String networkFile) {
        String coordSystem = "EPSG:2223";
        Scenario scenario = ScenarioUtils.loadScenario(ConfigUtils.createConfig());
        NetworkHandler.readOsmWriteMatsim(osmFile, networkFile, coordSystem, scenario);
    }

    public static void runMATsimFromConfig(String configFile) {
        Config config = ConfigUtils.createConfig();
        ConfigUtils.loadConfig(config, configFile);
        DynamicConfig configHandler = new DynamicConfig(config);
        Scenario scenario = ScenarioUtils.loadScenario(configHandler.getConfig());
        Controler controler = new Controler(scenario);

        // If we try and overwrite existing modes MATsim yells at us
//        String[] implementedModes = new String[TransportMode.class.getDeclaredFields().length];
//        for (int i = 0; i < implementedModes.length; i++){
//            implementedModes[i] = TransportMode.class.getDeclaredFields()[i].getName();
//        }

//        for (String mode : configHandler.getModes()) {
//            if (!Arrays.asList(implementedModes).contains(mode)) {
//                controler.addOverridingModule(new AbstractModule() {
//                    @Override
//                    public void install() {
//                        addTravelTimeBinding(mode).to(networkTravelTime());
//                        addTravelDisutilityFactoryBinding(mode).to(carTravelDisutilityFactoryKey());
//                    }
//                });
//            }
//        }
//        controler.addOverridingModule(new AbstractModule() {
//                    @Override
//                    public void install() {
//                        addTravelTimeBinding("car").to(networkTravelTime());
//                        addTravelDisutilityFactoryBinding("car").to(carTravelDisutilityFactoryKey());
//                    }
//                });
        controler.run();
    }
}
