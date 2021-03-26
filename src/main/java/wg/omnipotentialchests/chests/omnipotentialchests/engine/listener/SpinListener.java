package wg.omnipotentialchests.chests.omnipotentialchests.engine.listener;

import jdk.nashorn.api.scripting.JSObject;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.json.simple.JSONArray;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerFinishedSpinningEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerStartSpinningEvent;

public class SpinListener implements Listener {

    public void init(){
        Bukkit.getPluginManager().registerEvents(this, OmnipotentialChests.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void startSpinningHandler(PlayerStartSpinningEvent e){
        e.getChestGui().startSpinning();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void finishedSpinningHandler(PlayerFinishedSpinningEvent e){
        e.getPlayer().getInventory().addItem(e.getRewardItem().getItem());
    }
}
