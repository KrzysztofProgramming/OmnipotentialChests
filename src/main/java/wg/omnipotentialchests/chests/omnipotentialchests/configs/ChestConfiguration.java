package wg.omnipotentialchests.chests.omnipotentialchests.configs;


import wg.omnipotentialchests.chests.omnipotentialchests.configs.resourcesConfigGenerator.ConfigAccessor;

public class ChestConfiguration extends ConfigAccessor {

    public String path = "Configuration.";
    private int minimumItemCount;
    private int minimumSpinCount;


    public void init() {
        super.init("ChestConfiguration");
        this.minimumItemCount = getIntPath(path + "minimumItemCount");
        this.minimumSpinCount = getIntPath(path + "minimumSpinCount");
    }

    public int getMinimumItemCount() {
        return minimumItemCount;
    }

    public int getMinimumSpinCount() {
        return minimumSpinCount;
    }

}
