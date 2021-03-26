package wg.omnipotentialchests.chests.omnipotentialchests.configs;

import org.bukkit.configuration.file.YamlConfiguration;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;

import java.io.File;
import java.io.IOException;

public class YmlGenerator {

    private OmnipotentialChests plugin;
    private File folder;

    public void init() {
        this.plugin = OmnipotentialChests.getInstance();
        this.folder = new File(this.plugin.getDataFolder() + "/configs");
    }

    private File initConfig(String filename) {
        return new File(folder, filename + ".yml");
    }

    public void generateYml(String configName,String dataPath, String data) {
        File file = initConfig(configName);
        YamlConfiguration yml = makeFileIfNotExist(file);
        yml.addDefault(dataPath, data);
        saveConfig(file, yml);
    }

    private YamlConfiguration makeFileIfNotExist(File file) {
        YamlConfiguration yml = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
                yml = YamlConfiguration.loadConfiguration(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return yml;
    }

    private void deleteFiles() {
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                file.delete();
            }
//            makeLogsFolderIfNotExists();
        }
    }

    private boolean makeLogsFolderIfNotExists() {
        if (!folder.exists()) {
            return folder.mkdir();
        }
        return true;
    }

    private void saveConfig(File file, YamlConfiguration yml) {
        save(file, yml);
    }

    private boolean save(File file, YamlConfiguration yml) {
        makeLogsFolderIfNotExists();
        makeFileIfNotExist(file);
        try {
            yml.options().copyDefaults(true);
            yml.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
