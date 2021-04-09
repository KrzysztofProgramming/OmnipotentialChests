package wg.omnipotentialchests.chests.omnipotentialchests.engine.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.LoreManager;
import wg.omnipotentialchests.chests.omnipotentialchests.ultimateguis.engine.basics.BasicGui;

public class ChestItem extends ItemStack {
    public static final String CHEST_LORE_PHRASE = "ą<ę";

    public ChestItem(String chestName) {
        super(Material.CHEST);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(OmnipotentialChests.convertColors(chestName));
        this.setItemMeta(meta);
        LoreManager.applyEnchant(this, CHEST_LORE_PHRASE + BasicGui.clearColors(chestName));
    }

    public static String getChestName(ItemStack item) {
        return LoreManager.getEnchants(item).stream()
                .filter(enchant -> enchant.startsWith(CHEST_LORE_PHRASE))
                .map(enchant -> enchant.substring(CHEST_LORE_PHRASE.length()))
                .findAny().orElse(null);
    }
}
