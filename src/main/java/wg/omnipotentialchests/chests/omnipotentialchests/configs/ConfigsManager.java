package wg.omnipotentialchests.chests.omnipotentialchests.configs;

import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;

public class ConfigsManager {

     public YmlGenerator ymlGenerator;

     public ConfigsManager() {
         this.ymlGenerator = new YmlGenerator();
     }

     public void init() {
         this.ymlGenerator.init();
     }
}
