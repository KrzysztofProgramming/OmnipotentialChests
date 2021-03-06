package wg.omnipotentialchests.chests.omnipotentialchests.configs;

import lombok.Getter;
import wg.omnipotentialchests.chests.omnipotentialchests.configs.resourcesConfigGenerator.ConfigGenerator;

public class ConfigsManager {

    @Getter
    private final ChestConfiguration chestConfiguration;

    @Getter
    private final ConfigGenerator configGenerator;

    public ConfigsManager() {
        this.configGenerator = new ConfigGenerator();
        this.chestConfiguration = new ChestConfiguration();
    }

    public void init() {
        this.configGenerator.init();
        this.chestConfiguration.init();
    }

}
