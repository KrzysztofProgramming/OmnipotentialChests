package wg.omnipotentialchests.chests.omnipotentialchests.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.JSONTreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.JSONTreasureItem;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Database extends CustomSQLInterface {

    private final String chestTable = "chestTable";
    private final String chestName = "chestName";
    private final String chestJSON = "chestJSON";

    private interface DatabaseOperation<T> {
        T operate(ResultSet rs) throws SQLException;
    }

    private interface DatabaseInsertion {
        void insert(PreparedStatement pstmt) throws SQLException;
    }

    private class Worker<T> {
        public T getSomething(DatabaseOperation<T> operation, String query) {
            T temp = null;
            try (Connection conn = Database.this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();
                temp = operation.operate(rs);
                close(pstmt);
            } catch (SQLException e) {
               //
            }
            return temp;
        }
    }

    private void delete(String query) {
        try (Connection conn = Database.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
           //
        }
    }

    private void insertSomething(DatabaseInsertion operation, String query) {
        try (Connection conn = Database.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            operation.insert(pstmt);
            pstmt.executeUpdate();
            close(pstmt);
        } catch (SQLException e) {
          //
        }
    }

    private void createTable(String query, String databaseUrl) {
        try (Connection conn = DriverManager.getConnection(databaseUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            //
        }
    }

    public void init() {
        super.init("Chests");
        CheckIfDatabaseExists();
        createChestTable(chestTable, chestName, chestJSON);
    }

    private void createChestTable(String chestTable, String chestName, String chestJSON) {
        String sql = "CREATE TABLE IF NOT EXISTS " + chestTable + " (" + chestName + " TEXT NOT NULL, " + chestJSON + " TEXT NOT NULL);";
        createTable(sql, this.databaseUrl);
    }

    public void insertNewChest(String chestName , TreasureChest chestJSON) {
        String sql = "INSERT INTO " + chestTable + " (" + this.chestName + ", " + this.chestJSON + ") VALUES(?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, chestName);
            pstmt.setString(2, changeToJSONChest(chestJSON));
        }, sql);
    }

    public List<TreasureChest> getAllChests() {
        String sql = "SELECT * FROM " + chestTable;
        return new Worker<List<TreasureChest>>().getSomething(rs -> {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            List<TreasureChest> treasureChests = new ArrayList<>();
            while(rs.next()) {
                JSONTreasureChest[] jsonElement1 = gson.fromJson(rs.getString(this.chestJSON), JSONTreasureChest[].class);
                List<JSONTreasureChest> treasureChests2 = new ArrayList<>(Arrays.asList(jsonElement1.clone()));
                for (JSONTreasureChest chest : treasureChests2) {
                    List<JSONTreasureItem> jsonTreasureItemList = chest.getTreasureItems();
                    List<TreasureItem> treasureItems = jsonTreasureItemList.stream().map(item -> new TreasureItem(getItemStackFromBase64(item.getBase64ItemStack()), item.getChance())).collect(Collectors.toList());
                    TreasureChest treasureChest = new TreasureChest();
                    treasureChest.setName(chest.getName());
                    treasureChest.setTreasureItems(treasureItems);
                    treasureChests.add(treasureChest);
                }
            }
            return treasureChests;
        }, sql);
    }

    public void deleteChest(String chestName) {
        String sql = "DELETE FROM " + chestTable + " WHERE " + this.chestName + " = " +  "\"" + chestName + "\"";
        delete(sql);
    }

    private String changeToJSONChest(TreasureChest treasureChest){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        List<JSONTreasureChest> treasureChests2 = new ArrayList<>();
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
        return gson.toJson(treasureChests2);
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
