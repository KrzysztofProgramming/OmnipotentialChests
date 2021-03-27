package wg.omnipotentialchests.chests.omnipotentialchests.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.JSONTreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.JSONTreasureItem;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
            file.write("[{}]");
            file.flush();
            file.close();
        }
        return generatedFile;
    }

    private JSONTreasureChest getSpecificJSONObject(List<JSONTreasureChest> treasureChests2, String treasureChestName) {
        for (JSONTreasureChest treasure : treasureChests2) {
            if (treasure.getName().equals(treasureChestName)) {
                return treasure;
            }
        }
        return null;
    }

    @SneakyThrows
    public TreasureChest readJSONFile(String filename, String treasureChestName) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        File jsonFile = new File(folder + "/" + filename + ".json");
        String jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);

        JSONTreasureChest[] jsonElement1 = gson.fromJson(jsonString, JSONTreasureChest[].class);
        List<JSONTreasureChest> treasureChests2 = new ArrayList<>(Arrays.asList(jsonElement1.clone()));
        JSONTreasureChest jsonTreasureChest = getSpecificJSONObject(treasureChests2, treasureChestName);

        assert jsonTreasureChest != null;
        List<JSONTreasureItem> jsonTreasureItemList = jsonTreasureChest.getTreasureItems();
        List<TreasureItem> treasureItems = jsonTreasureItemList.stream().map(item -> new TreasureItem(getItemStackFromBase64(item.getBase64ItemStack()), item.getChance())).collect(Collectors.toList());

        TreasureChest treasureChest = new TreasureChest();
        treasureChest.setName(treasureChestName);
        treasureChest.setTreasureItems(treasureItems);
        return treasureChest;
    }

    public void addObjectToExistingFile(String filename, TreasureChest treasureChest) {
        this.addToExistingFileByPath(filename, treasureChest);
    }

    @SneakyThrows
    private void addToExistingFileByPath(String filename, TreasureChest treasureChest) {
        File jsonFile = new File(folder + "/" + filename + ".json");
        String jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        JSONTreasureChest[] jsonElement1 = gson.fromJson(jsonString, JSONTreasureChest[].class);
        List<JSONTreasureChest> treasureChests2 = new ArrayList<>(Arrays.asList(jsonElement1.clone()));
        if (treasureChests2.contains(new JSONTreasureChest(null, new ArrayList<>()))) {
            treasureChests2.clear();
        }
        JSONTreasureChest base64TreasureChest = new JSONTreasureChest();
        base64TreasureChest.setName(treasureChest.getName());
        List<JSONTreasureItem> jsonTreasureItem = treasureChest.getTreasureItems().parallelStream().map(item -> {
            JSONTreasureItem jsonT = new JSONTreasureItem();
            jsonT.setChance(item.getChance());
            jsonT.setBase64ItemStack(this.serializeBase64FromItemStack(item.getItem()));
            return jsonT;
        }).collect(Collectors.toList());
        base64TreasureChest.setTreasureItems(jsonTreasureItem);
        treasureChests2.add(base64TreasureChest);
        FileUtils.writeStringToFile(jsonFile, gson.toJson(treasureChests2), StandardCharsets.UTF_8);
    }

    private boolean makeLogsFolderIfNotExists() {
        if (!folder.exists()) {
            return folder.mkdir();
        }
        return true;
    }

    @SneakyThrows
    private ItemStack getItemStackFromBase64(String base64) {
        ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
        BukkitObjectInputStream is = new BukkitObjectInputStream(in);
        return (ItemStack) is.readObject();
    }

    @SneakyThrows
    private String serializeBase64FromItemStack(ItemStack itemStack) {
        ByteArrayOutputStream io = new ByteArrayOutputStream();
        BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
        os.writeObject(itemStack);
        os.flush();
        byte[] serializedObject = io.toByteArray();
        return Base64.getEncoder().encodeToString(serializedObject);
    }

}
