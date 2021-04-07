package wg.omnipotentialchests.chests.omnipotentialchests.engine;

import ad.guis.ultimateguis.engine.basics.BasicGui;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreManager {
    public static final char COLOR_CHAR = '§';
    public static final String SEPARATOR = "ł作ツ";
    public static final String INV_SEPARATOR = toInvisibleLore(SEPARATOR);
    public static final String PREFIX = "にź走ń";
    public static final String INV_PREFIX = toInvisibleLore(PREFIX);


    public static void applyEnchant(ItemStack item, String enchant) {
        if (hasEnchant(item, enchant)) return;
        enchant = BasicGui.clearColors(enchant);

        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            List<String> loreList = meta.getLore();
            int enchantIndex = enchantsLineNumber(loreList);
            if (enchantIndex < 0) {
                loreList.add(createNewEnchant(enchant));
                return;
            }
            loreList.set(enchantIndex, addEnchant(loreList.get(enchantIndex), enchant));
            meta.setLore(loreList);
        } else {
            List<String> loreList = new ArrayList<>();
            loreList.add(createNewEnchant(enchant));
            meta.setLore(loreList);
        }
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
        int index = enchantsLineNumber(loreList);
        if (index < 0) return;

        loreList.remove(index);
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void removeEnchant(ItemStack item, String enchant) {
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return;

        enchant = BasicGui.clearColors(enchant);

        List<String> loreList = meta.getLore();
        int index = enchantsLineNumber(loreList);
        if (index < 0) return;

        loreList.set(index, loreList.get(index)
                .replaceAll(toInvisibleLore(enchant) + INV_SEPARATOR, ""));

        if (loreList.get(index).equals(INV_PREFIX)) {
            loreList.remove(index);
        }

        meta.setLore(loreList);
        item.setItemMeta(meta);
    }


    public static List<String> getEnchants(ItemStack item) {
        List<String> enchantsList = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return enchantsList;

        List<String> loreList = meta.getLore();
        int index = enchantsLineNumber(loreList);
        if (index < 0) return enchantsList;

        return Arrays.asList(fromInvisibleLore(loreList.get(index))
                .substring(PREFIX.length()).split(SEPARATOR));
    }

    public static boolean hasEnchant(ItemStack item, String enchant) {
        enchant = BasicGui.clearColors(enchant);
        return getEnchants(item).contains(enchant);
    }

    private static String createNewEnchant(String newEnchant) {
        return INV_PREFIX + toInvisibleLore(newEnchant) + INV_SEPARATOR;
    }

    private static String addEnchant(String loreLine, String newEnchant) {
        return loreLine + toInvisibleLore(newEnchant) + INV_SEPARATOR;
    }

    public static String toInvisibleLore(String lore) {
        StringBuilder builder = new StringBuilder();
        for (char a : lore.toCharArray()) {
            if(a==' '){
             builder.append(a);
            }
            else {
                builder.append(COLOR_CHAR).append(a);
            }
        }
        return builder.toString();
    }

    private static int enchantsLineNumber(List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).startsWith(INV_PREFIX))
                return i;
        }
        return -1;
    }

    public static String fromInvisibleLore(String lore) {
        return lore.replaceAll(String.valueOf(COLOR_CHAR), "");
    }

}
