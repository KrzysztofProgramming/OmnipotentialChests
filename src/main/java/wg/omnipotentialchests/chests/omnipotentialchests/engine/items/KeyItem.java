package wg.omnipotentialchests.chests.omnipotentialchests.engine.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.LoreManager;
import wg.omnipotentialchests.chests.omnipotentialchests.ultimateguis.engine.basics.BasicGui;

import java.util.List;
import java.util.stream.Collectors;

public class KeyItem extends ItemStack {
    public static final String KEY_LORE_START = OmnipotentialChests.convertColors("&fCan open ");
    public static final String KEY_LORE_END = OmnipotentialChests.convertColors(" &r&fchest");
    public static final String CLEARED_KEY_LORE_START = BasicGui.clearColors(KEY_LORE_START);
    public static final String CLEARED_KEY_LORE_END = BasicGui.clearColors(KEY_LORE_END);

    public KeyItem(String chestName) {
        super(Material.TRIPWIRE_HOOK);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(OmnipotentialChests.convertColors(chestName + " &r&fkey"));
        this.setItemMeta(meta);
        LoreManager.applyEnchant(this, KEY_LORE_START + chestName + KEY_LORE_END);
    }


    public static boolean canOpenThisChest(ItemStack item, String chestName) {
        return getOpenableChests(item).contains(chestName);
    }

    public static List<String> getOpenableChests(ItemStack item) {
        return LoreManager.getEnchants(item).stream()
                .filter(enchant -> enchant.endsWith(CLEARED_KEY_LORE_END) && enchant.startsWith(CLEARED_KEY_LORE_START))
                .map(enchant -> enchant.substring(0, enchant.length() - CLEARED_KEY_LORE_END.length())
                        .substring(CLEARED_KEY_LORE_START.length()))
                .collect(Collectors.toList());
    }

}
