package archive;

public class Testing {
    public Testing(){
        DynamicConfig dynamicConfig = new DynamicConfig();
        dynamicConfig.updateConfigFromSpec("testing_data/AZ_full_simulation_config_specifications.xml");

        dynamicConfig.writeConfig("testing_data/AZ_full_config.xml");
    }

    public static void main(String[] args){
        Testing testing = new Testing();
    }

}
