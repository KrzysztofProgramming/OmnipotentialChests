package wg.omnipotentialchests.chests.omnipotentialchests.configs;

import lombok.Getter;

public class ConfigsManager {

    @Getter
    private JSONGenerator JSONGenerator;

    public ConfigsManager() {
        this.JSONGenerator = new JSONGenerator();
    }

    public void init() {
        this.JSONGenerator.init();
    }
}
