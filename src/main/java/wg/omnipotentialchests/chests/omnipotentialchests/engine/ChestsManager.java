package wg.omnipotentialchests.chests.omnipotentialchests.engine;

import ad.guis.ultimateguis.engine.basics.BasicGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.configs.JSONGenerator;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.base.ChestGui;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.items.ChestItem;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.items.KeyItem;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class ChestsManager implements Listener {
    private JSONGenerator jsonGenerator;
    private Map<String, TreasureChest> treasureChestMap = new HashMap<>();
    private final static String fileName = "chests";


    public void init(){
        jsonGenerator = OmnipotentialChests.getInstance().getConfigsManager().getJSONGenerator();
        this.setChest(TreasureChest.getExample());
        treasureChestMap = jsonGenerator.getAllChests(fileName).stream().collect(Collectors.toMap(
                treasureChest -> BasicGui.clearColors(treasureChest.getName()), treasureChest -> treasureChest
        ));
        Bukkit.getPluginManager().registerEvents(this, OmnipotentialChests.getInstance());
    }

    public TreasureChest getTreasureChest(String name){
        return treasureChestMap.get(BasicGui.clearColors(name));
    }

    public Collection<TreasureChest> getAllTreasureChests(){
    return treasureChestMap.values();
    }

    public List<String> getTreasureChestsNames(){
        return treasureChestMap.keySet().stream().map(BasicGui::clearColors).collect(Collectors.toList());
    }

    public void setChest(TreasureChest chest){
        this.treasureChestMap.put(BasicGui.clearColors(chest.getName()), chest);
        this.saveToDatabase(chest);
    }

    public void removeChest(String treasureChestName){
        this.treasureChestMap.remove(treasureChestName);
        this.removeFromDatabase(treasureChestName);
    }

    private void saveToDatabase(TreasureChest chest){
       jsonGenerator.editTreasureChest(fileName, chest.getName(), chest);
    }

    private void removeFromDatabase(String chestName){
        //todo
    }


    public void disable(){
    }


    private boolean openChest(String chestName, Player player){
        TreasureChest chest = this.getTreasureChest(chestName);
        if(chest == null) return false;
        ChestGui chestGui = new ChestGui(chest);
        chestGui.startSpinning(player);
        return true;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void detectChestOpen(PlayerInteractEvent e) {
       if (e.getItem() == null) return;
       if (e.getItem().getItemMeta() == null) return;
       if (e.getAction() != Action.RIGHT_CLICK_AIR
               && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

       if(!KeyItem.getOpenableChests(e.getItem()).isEmpty())  e.setCancelled(true);

       String openedChestName = ChestItem.getChestName(e.getItem());
       if(openedChestName == null) return;

       e.setCancelled(true);
       ItemStack[] itemsToRemove = new ItemStack[2];

       boolean match = false;
       for(ItemStack item: e.getPlayer().getInventory().getStorageContents()){
           if(item==null) continue;
           for(String openableChests: KeyItem.getOpenableChests(item)){
               if(openableChests.equals(openedChestName)){
                   match = true;
                   itemsToRemove[0] = item;
                   itemsToRemove[1] = e.getItem();
                   break;
               }
           }
       }

       if(match && openChest(openedChestName, e.getPlayer())){
            for(ItemStack itemToRemove: itemsToRemove){
                itemToRemove.setAmount(itemToRemove.getAmount() - 1);
            }
       }
       else if(!match){
           e.getPlayer().sendMessage(OmnipotentialChests
                   .convertColors("&cYou don't have a dedicated key to open this chest"));
       }
       else{
           e.getPlayer().sendMessage(OmnipotentialChests.convertColors(
                   "&eSorry, but this chests has been removed"));
       }
    }

}
