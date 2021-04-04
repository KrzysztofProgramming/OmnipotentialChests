package wg.omnipotentialchests.chests.omnipotentialchests.engine.base;

import ad.guis.ultimateguis.engine.basics.BasicGui;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.creators.ChestCreatorGui;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerFinishedSpinningEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerStartSpinningEvent;

public class SpinListener implements Listener {

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, OmnipotentialChests.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void startSpinningHandler(PlayerStartSpinningEvent e) {
        e.getChestGui().open(e.getPlayer());
        e.getChestGui().startSpinningWithoutEvent();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void finishedSpinningHandler(PlayerFinishedSpinningEvent e) {
        e.getChestGui().setRewardBackground();
        BasicGui.returnItemToPlayer(e.getPlayer(), e.getRewardItem().getItem());
        e.getPlayer().sendMessage(OmnipotentialChests.convertColors(
                "&aYour reward is: &r" + ChestCreatorGui.getItemName(e.getRewardItem().getItem())));
    }
}
