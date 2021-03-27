package wg.omnipotentialchests.chests.omnipotentialchests.configs;

public class ConfigsManager {

    public JSONGenerator JSONGenerator;

    public ConfigsManager() {
        this.JSONGenerator = new JSONGenerator();
    }

    public void init() {
        this.JSONGenerator.init();
    }
}
