package wg.omnipotentialchests.chests.omnipotentialchests.configs;


import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.configs.resourcesConfigGenerator.ConfigAccessor;
import wg.omnipotentialchests.chests.omnipotentialchests.ultimateguis.engine.ANSIColors;

public class ChestConfiguration extends ConfigAccessor {

    public String path = "Configuration.";
    private int minimumItemCount;
    private int minimumSpinCount;


    public void init() {
        super.init("ChestConfiguration");
        this.minimumItemCount = getIntPath(path + "minimumItemCount");
        if(this.minimumItemCount < 9){
            OmnipotentialChests.getInstance().getLogger().warning(ANSIColors.ANSI_RED
                    + "minimumItemCount can't be lower than 9, please check your config" + ANSIColors.ANSI_RESET);
            this.minimumItemCount = 9;
        }

        this.minimumSpinCount = getIntPath(path + "minimumSpinCount");
    }

    public int getMinimumItemCount() {
        return minimumItemCount;
    }

    public int getMinimumSpinCount() {
        return minimumSpinCount;
    }

}
