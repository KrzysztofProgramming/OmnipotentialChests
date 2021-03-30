package wg.omnipotentialchests.chests.omnipotentialchests.engine;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.configs.JSONGenerator;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.items.KeyItem;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ChestsManager implements Listener {
    private JSONGenerator jsonGenerator;
    private Map<String, TreasureChest> treasureChestMap = new HashMap<>();
    private final static String fileName = "chests";


    public void init(){
        jsonGenerator = OmnipotentialChests.getInstance().getConfigsManager().getJSONGenerator();
        treasureChestMap = jsonGenerator.getAllChests(fileName).stream().collect(Collectors.toMap(
                TreasureChest::getName, treasureChest -> treasureChest
        ));


        Bukkit.getPluginManager().registerEvents(this, OmnipotentialChests.getInstance());
    }

    public TreasureChest getTreasureChest(String name){
        return treasureChestMap.get(name);
    }

    public Collection<TreasureChest> getAllTreasureChests(){
        return treasureChestMap.values();
    }

    public void disable(){
    }

   @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
   private void detectChestOpen(PlayerInteractEvent e){

   }

}
