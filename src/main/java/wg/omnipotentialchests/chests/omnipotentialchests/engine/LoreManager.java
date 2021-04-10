package wg.omnipotentialchests.chests.omnipotentialchests.engine;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.ultimateguis.engine.basics.BasicGui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoreManager {
    public static final char COLOR_CHAR = '§';
//    public static final String SEPARATOR = "ł,%";
//    public static final String INV_SEPARATOR = toInvisibleLore(SEPARATOR);
    public static final String PREFIX = "ź>ńż";
    public static final String INV_PREFIX = toInvisibleLore(PREFIX);


    public static void applyEnchant(ItemStack item, String enchant) {
        enchant = OmnipotentialChests.convertColors(enchant);
        if (hasEnchant(item, enchant)) return;

        ItemMeta meta = item.getItemMeta();
        List<String> loreList;
        if (meta.hasLore()) {
            loreList = meta.getLore();

        } else {
            loreList = new ArrayList<>();
        }
        loreList.add(INV_PREFIX + enchant);
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void applyEnchants(ItemStack item, String... enchants) {
        for (String enchant : enchants) {
            applyEnchant(item, enchant);
        }
    }

    public static void clearEnchants(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return;

        List<String> loreList = meta.getLore();
        loreList.removeIf(line->line.startsWith(INV_PREFIX));
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void removeEnchant(ItemStack item, final String enchant) {
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return;

        List<String> loreList = meta.getLore();
        loreList.removeIf(line->BasicGui.clearColors(line).equals(PREFIX + BasicGui.clearColors(enchant)));
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }


    public static List<String> getEnchants(ItemStack item) {
        List<String> enchantsList = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return enchantsList;

        List<String> loreList = meta.getLore();
        return loreList.stream()
                .filter(line->line.startsWith(INV_PREFIX))
                .map(line->BasicGui.clearColors(line.substring(INV_PREFIX.length())))
                .collect(Collectors.toList());
    }

    public static boolean hasEnchant(ItemStack item, String enchant) {
        enchant = BasicGui.clearColors(enchant);
        return getEnchants(item).contains(enchant);
    }

    public static String toInvisibleLore(String lore) {
        StringBuilder builder = new StringBuilder();
        for (char a : lore.toCharArray()) {
            builder.append(COLOR_CHAR).append(a);
        }
        return builder.toString();
    }

}
