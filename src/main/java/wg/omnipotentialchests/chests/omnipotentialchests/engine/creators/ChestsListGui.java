package wg.omnipotentialchests.chests.omnipotentialchests.engine.creators;

import ad.guis.ultimateguis.engine.basics.BasicGui;
import ad.guis.ultimateguis.engine.basics.ListGui;
import ad.guis.ultimateguis.engine.interfaces.BasicAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.ChestsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;

import java.util.ArrayList;

public class ChestsListGui extends ListGui<TreasureChest> {
    private final ChestsManager chestsManager;

    public ChestsListGui(BasicGui previousGui) {
        super(previousGui, "Treasure chests");
        this.chestsManager = OmnipotentialChests.getInstance().getListenersManager().getChestsManager();
        this.setRefreshFunction(() -> new ArrayList<>(chestsManager.getAllTreasureChests()));
        this.setAction(this::leftClickAction);
        this.setRightAction(this::rightClickAction);
    }

    public void leftClickAction(TreasureChest chest){
        BasicGui.returnItemToPlayer(this.getLastClicker(), chest.getChestItem(), chest.getKeyItem());
    }

    public void rightClickAction(TreasureChest chest){
        new ChestCreatorGui(chest, this).open(this.getLastClicker());
    }

    @Override
    public ItemStack getDescriptionItem(TreasureChest treasureChest) {
        return BasicGui.createItem(Material.CHEST, treasureChest.getName(),
                BasicGui.simpleSplitLore(OmnipotentialChests.convertColors("&7&lLEFT &r&7click to get chest"),
                        OmnipotentialChests.convertColors("&7&lRIGHT &r&7click to edit chest")));
    }
}
