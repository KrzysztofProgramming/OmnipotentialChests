package wg.omnipotentialchests.chests.omnipotentialchests.engine.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.LoreManager;

import java.util.List;
import java.util.stream.Collectors;

public class ChestItem extends ItemStack {
    public static final String CHEST_LORE_PHRASE = "C萨©";

    public ChestItem(String chestName){
        super(Material.CHEST);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(OmnipotentialChests.convertColors(chestName));
        this.setItemMeta(meta);
        LoreManager.applyEnchant(this, CHEST_LORE_PHRASE + chestName);
    }

    public static String getChestName(ItemStack item){
        return LoreManager.getEnchants(item).stream()
                .filter(enchant -> enchant.startsWith(CHEST_LORE_PHRASE))
                .map(enchant -> enchant.substring(CHEST_LORE_PHRASE.length()))
                .findAny().orElse(null);
    }
}
