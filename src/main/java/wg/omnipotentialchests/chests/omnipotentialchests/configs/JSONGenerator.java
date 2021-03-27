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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class JSONGenerator {

    private OmnipotentialChests plugin;
    private File folder;
    private File mainFolder;

    public void init() {
        this.plugin = OmnipotentialChests.getInstance();
        this.mainFolder = new File(this.plugin.getDataFolder() + "");
        this.folder = new File(this.plugin.getDataFolder() + "/configs");
        creteJsonFolders();
    }

    private void creteJsonFolders() {
        this.makeMainFolder();
        this.makeChestFolder();
    }

    private boolean makeMainFolder() {
        if (!mainFolder.exists()) {
            return mainFolder.mkdir();
        }
        return true;
    }

    private boolean makeChestFolder() {
        if (!folder.exists()) {
            return folder.mkdir();
        }
        return true;
    }

    @SneakyThrows
    public File generateJSONFile(String filename) {
        creteJsonFolders();
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
            if (treasure.getName() == null) {
                return null;
            }
            if (treasure.getName().equals(treasureChestName)) {
                return treasure;
            }
        }
        return null;
    }

    public void editTreasureChest(String filename, String treasureChestName, TreasureChest editedTreasureChest) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        File jsonFile = new File(folder + "/" + filename + ".json");
        String jsonString;
        try {
            jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            generateJSONFile(filename);
            this.addToExistingFileByPath(filename, editedTreasureChest);
            return;
        }
        JSONTreasureChest[] jsonElement1 = gson.fromJson(jsonString, JSONTreasureChest[].class);
        List<JSONTreasureChest> treasureChests2 = new ArrayList<>(Arrays.asList(jsonElement1.clone()));
        treasureChests2.removeIf(item -> item.getName().equals(treasureChestName));
        try {
            FileUtils.writeStringToFile(jsonFile, gson.toJson(treasureChests2), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
        this.addToExistingFileByPath(filename, editedTreasureChest);
    }

    public List<TreasureChest> getAllChests(String filename) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        File jsonFile = new File(folder + "/" + filename + ".json");
        String jsonString;
        try {
            jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            generateJSONFile(filename);
            return new ArrayList<>();
        }
        JSONTreasureChest[] jsonElement1 = gson.fromJson(jsonString, JSONTreasureChest[].class);
        List<JSONTreasureChest> treasureChests2 = new ArrayList<>(Arrays.asList(jsonElement1.clone()));
        List<TreasureChest> treasureChests = new ArrayList<>();
        for (JSONTreasureChest chest : treasureChests2) {
            List<JSONTreasureItem> jsonTreasureItemList = chest.getTreasureItems();
            List<TreasureItem> treasureItems = jsonTreasureItemList.stream().map(item -> new TreasureItem(getItemStackFromBase64(item.getBase64ItemStack()), item.getChance())).collect(Collectors.toList());
            TreasureChest treasureChest = new TreasureChest();
            treasureChest.setName(chest.getName());
            treasureChest.setTreasureItems(treasureItems);
            treasureChests.add(treasureChest);
        }
        return treasureChests;
    }

    public TreasureChest readJSONFile(String filename, String treasureChestName) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        File jsonFile = new File(folder + "/" + filename + ".json");
        String jsonString;
        try {
            jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            generateJSONFile(filename);
            return readJSONFile(filename, treasureChestName);
        }
        JSONTreasureChest[] jsonElement1 = gson.fromJson(jsonString, JSONTreasureChest[].class);
        List<JSONTreasureChest> treasureChests2 = new ArrayList<>(Arrays.asList(jsonElement1.clone()));
        JSONTreasureChest jsonTreasureChest = getSpecificJSONObject(treasureChests2, treasureChestName);
        if (jsonTreasureChest == null) {
            return new TreasureChest();
        }
        List<JSONTreasureItem> jsonTreasureItemList = jsonTreasureChest.getTreasureItems();
        List<TreasureItem> treasureItems = jsonTreasureItemList.stream().map(item ->
                new TreasureItem(getItemStackFromBase64(item.getBase64ItemStack()), item.getChance()))
                .collect(Collectors.toList());
        TreasureChest treasureChest = new TreasureChest();
        treasureChest.setName(treasureChestName);
        treasureChest.setTreasureItems(treasureItems);
        return treasureChest;
    }

    public void addObjectToExistingFile(String filename, TreasureChest treasureChest) {
        this.addToExistingFileByPath(filename, treasureChest);
    }

    private void addToExistingFileByPath(String filename, TreasureChest treasureChest) {
        File jsonFile = new File(folder + "/" + filename + ".json");
        String jsonString;
        try {
            jsonString = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            generateJSONFile(filename);
            this.addToExistingFileByPath(filename, treasureChest);
            return;
        }
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
        if (!this.checkIfTreasureChestExists(base64TreasureChest.getName(), treasureChests2)) {
            treasureChests2.add(base64TreasureChest);
        }
        try {
            FileUtils.writeStringToFile(jsonFile, gson.toJson(treasureChests2), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
    }

    private boolean checkIfTreasureChestExists(String treasureChestName, List<JSONTreasureChest> jsonTreasureChestList) {
        for (JSONTreasureChest chest : jsonTreasureChestList) {
            if (chest.getName().equals(treasureChestName)) {
                return true;
            }
        }
        return false;
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
