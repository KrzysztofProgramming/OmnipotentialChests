package wg.omnipotentialchests.chests.omnipotentialchests.engine.creators;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.LoreManager;

import java.util.ArrayList;
import java.util.List;

public class PercentManager {
    private final static String PERCENT_MODIFIER = "すó為'";
    private final static String INV_PERCENT_MODIFIER = LoreManager.toInvisibleLore(PERCENT_MODIFIER);

    public static void removePercentFromLore(ItemStack item) {
        if (item == null || item.getItemMeta() == null || !item.getItemMeta().hasLore()) return;
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.removeIf(line -> line.startsWith(INV_PERCENT_MODIFIER));
//        if(lore.isEmpty())
//            lore = null;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }


    public static void showPercentValue(ItemStack item, double chance) {
        if (item == null || item.getItemMeta() == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            int index = getPercentLineIndex(lore);

            if (index < 0) {
                addPercentToLore(item, chance);
                return;
            }

            lore.set(index, INV_PERCENT_MODIFIER + ChatColor.AQUA
                    + Math.round(chance * 100) / 100.0 + '%');

            meta.setLore(lore);
            item.setItemMeta(meta);
            return;
        }
        addPercentToLore(item, chance);
    }

    public static void addPercentToLore(ItemStack item, double chance) {
        if (item == null || item.getItemMeta() == null) return;
        ItemMeta meta = item.getItemMeta();
        List<String> lore;
        if (meta.hasLore())
            lore = meta.getLore();
        else
            lore = new ArrayList<>();
        lore.add(INV_PERCENT_MODIFIER + ChatColor.AQUA
                + Math.round(chance * 100) / 100.0 + '%');
        meta.setLore(lore);
        item.setItemMeta(meta);
    }


    public static ItemStack removePercentValueAndCopy(ItemStack item) {
        ItemStack copy = item.clone();
        removePercentFromLore(copy);
        return copy;
    }

    private static int getPercentLineIndex(List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).startsWith(INV_PERCENT_MODIFIER))
                return i;
        }
        return -1;
    }
}
