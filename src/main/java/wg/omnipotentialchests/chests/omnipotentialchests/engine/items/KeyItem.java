package wg.omnipotentialchests.chests.omnipotentialchests.engine.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.LoreManager;

import java.util.List;
import java.util.stream.Collectors;

public class KeyItem extends ItemStack {
    public static final String KEY_LORE_PHRASE = "れąहै";

    public KeyItem(String chestName) {
        super(Material.TRIPWIRE_HOOK);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(OmnipotentialChests.convertColors(chestName + " &rkey"));
        this.setItemMeta(meta);
        LoreManager.applyEnchant(this, KEY_LORE_PHRASE + chestName);
    }


    public static boolean canOpenThisChest(ItemStack item, String chestName) {
        return getOpenableChests(item).contains(chestName);
    }

    public static List<String> getOpenableChests(ItemStack item) {
        return LoreManager.getEnchants(item).stream()
                .filter(enchant -> enchant.startsWith(KEY_LORE_PHRASE))
                .map(enchant -> enchant.substring(KEY_LORE_PHRASE.length()))
                .collect(Collectors.toList());
    }

}
