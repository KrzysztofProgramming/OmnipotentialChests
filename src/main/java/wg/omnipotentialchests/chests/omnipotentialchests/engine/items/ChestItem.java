package wg.omnipotentialchests.chests.omnipotentialchests.engine.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.LoreManager;
import wg.omnipotentialchests.chests.omnipotentialchests.ultimateguis.engine.basics.BasicGui;

public class ChestItem extends ItemStack {
    public static final String CHEST_LORE_END = OmnipotentialChests.convertColors(" &r&fkey is required");
    public static final String CLEARED_CHEST_LORE_END = BasicGui.clearColors(CHEST_LORE_END);

    public ChestItem(String chestName) {
        super(Material.CHEST);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(OmnipotentialChests.convertColors(chestName));
        this.setItemMeta(meta);
        LoreManager.applyEnchant(this,  OmnipotentialChests.convertColors("&f") + chestName + CHEST_LORE_END);
    }

    public static String getChestName(ItemStack item) {
        return LoreManager.getEnchants(item).stream()
                .filter(enchant -> enchant.endsWith(CLEARED_CHEST_LORE_END))
                .map(enchant -> enchant.substring(0, enchant.length() - CLEARED_CHEST_LORE_END.length()))
                .findAny().orElse(null);
    }
}
