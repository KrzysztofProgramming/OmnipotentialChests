package wg.omnipotentialchests.chests.omnipotentialchests.engine.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.LoreManager;

import java.util.List;

public class KeyItem extends ItemStack {
    public static final String KEY_LORE_PHRASE = "K洛®";

    public KeyItem(String chestName){
        super(Material.TRIPWIRE_HOOK);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(OmnipotentialChests.convertColors(chestName + " &rkey"));
        this.setItemMeta(meta);
    }
}
