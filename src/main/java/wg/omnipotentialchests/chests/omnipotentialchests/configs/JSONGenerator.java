package wg.omnipotentialchests.chests.omnipotentialchests.configs;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicReference;

public class JSONGenerator {

    private OmnipotentialChests plugin;
    private File folder;
    private boolean folderCreated;

    public void init() {
        this.plugin = OmnipotentialChests.getInstance();
        this.folder = new File(this.plugin.getDataFolder() + "/configs");
        this.folderCreated = makeLogsFolderIfNotExists();
        OmnipotentialChests.getInstance().getConfigsManager().JSONGenerator.generateJSONFile("1");
    }

    @SneakyThrows
    public File generateJSONFile(String filename) {
        File generatedFile = new File(folder + "/" + filename + ".json");
        if (!generatedFile.exists()) {
            FileWriter file = new FileWriter(folder + "/" + filename + ".json");
            file.write("{}");
            file.flush();
            file.close();
        }
        return generatedFile;
    }

    @SneakyThrows
    public TreasureChest readJSONFile(String filename, String keyValue) {
        JsonParser parser = new JsonParser();
        Reader reader = new FileReader(folder + "/" + filename + ".json");
        JsonElement json = parser.parse(reader);
        JsonObject object = json.getAsJsonObject();
        return new Gson()
                .fromJson(object.get(keyValue).toString()
                        .replaceAll("\"", ""), TreasureChest.class);
    }


    public void addObjectToExistingFile(String filename, String keyValue, TreasureChest treasureChest) {
        this.addToExistingFileByPath(filename, keyValue, treasureChest);
    }


    @SneakyThrows
    private void addToExistingFileByPath(String filename, String keyValue, TreasureChest treasureChest) {
        File jsonFile = new File(folder + "/" + filename + ".json");
        Gson gson = new Gson();
        String jsonString = FileUtils.readFileToString(jsonFile);
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        String json = gson.toJson(treasureChest);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty(keyValue, json.replaceAll("\"", "").replaceAll("/", ""));
        String resultingJson = gson.toJson(jsonElement);
        FileUtils.writeStringToFile(jsonFile, resultingJson);
    }

    private boolean makeLogsFolderIfNotExists() {
        if (!folder.exists()) {
            return folder.mkdir();
        }
        return true;
    }
}
