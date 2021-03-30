package wg.omnipotentialchests.chests.omnipotentialchests.engine;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreManager {
    public static final char COLOR_CHAR= '§';
    public static final String SEPARATOR = "S萨®";
    public static final String PREFIX = "*®埃A/©";


    public static void applyEnchant(ItemStack item, String enchant){
        if(hasEnchant(item, enchant)) return;

        ItemMeta meta = item.getItemMeta();
        if(meta.hasLore()){
            List<String> loreList = meta.getLore();
            int enchantIndex = enchantsLineNumber(loreList);
            if(enchantIndex < 0){
                loreList.add(createNewEnchant(enchant));
                return;
            }
            loreList.set(enchantIndex, addEnchant(loreList.get(enchantIndex), enchant));
            meta.setLore(loreList);
        }
        else{
            List<String> loreList = new ArrayList<>();
            loreList.add(createNewEnchant(enchant));
            meta.setLore(loreList);
        }
        item.setItemMeta(meta);
    }

    public static ItemStack applyEnchantClone(ItemStack item, String enchant){
        ItemStack copy = item.clone();
        applyEnchant(copy, enchant);
        return copy;
    }

    public static void applyEnchants(ItemStack item, String ...enchants){
        for(String enchant: enchants){
            applyEnchant(item, enchant);
        }
    }

    public static ItemStack applyEnchantsClone(ItemStack item, String ...enchants){
        ItemStack copy = item.clone();
        applyEnchants(copy, enchants);
        return copy;
    }

    public static void clearEnchants(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasLore()) return;

        List<String> loreList = meta.getLore();
        int index = enchantsLineNumber(loreList);
        if(index < 0) return;

        loreList.remove(index);
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static ItemStack clearEnchantsClone(ItemStack item){
        ItemStack copy = item.clone();
        clearEnchants(copy);
        return copy;
    }

    public static void removeEnchant(ItemStack item, String enchant){
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasLore()) return;

        List<String> loreList = meta.getLore();
        int index = enchantsLineNumber(loreList);
        if(index < 0) return;

        loreList.set(index, loreList.get(index)
                .replaceAll(toInvisibleLore(enchant + SEPARATOR), ""));

        if(loreList.get(index).equals(toInvisibleLore(PREFIX))) {
            loreList.remove(index);
        }

        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static ItemStack removeEnchantClone(ItemStack item, String enchant){
        ItemStack copy = item.clone();
        removeEnchant(copy, enchant);
        return copy;
    }

    public static List<String> getEnchants(ItemStack item){
        List<String> enchantsList = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasLore()) return enchantsList;

        List<String> loreList = meta.getLore();
        int index = enchantsLineNumber(loreList);
        if(index < 0) return enchantsList;

        return Arrays.asList(fromInvisibleLore(loreList.get(index))
                .substring(PREFIX.length()).split(SEPARATOR));
    }

    public static boolean hasEnchant(ItemStack item, String enchant){
        return getEnchants(item).contains(enchant);
    }

    private static String createNewEnchant(String newEnchant){
        return toInvisibleLore(PREFIX + newEnchant + SEPARATOR);
    }

    private static String addEnchant(String loreLine, String newEnchant){
        return loreLine + toInvisibleLore(newEnchant + SEPARATOR);
    }

    public static String toInvisibleLore(String lore){
        StringBuilder builder = new StringBuilder();
        for(char a: lore.toCharArray()){
            builder.append(COLOR_CHAR).append(a);
        }
        return builder.toString();
    }

    private static int enchantsLineNumber(List<String> lore){
        for(int i=0; i<lore.size(); i++){
            if(lore.get(i).startsWith(toInvisibleLore(PREFIX)))
                return i;
        }
        return -1;
    }

    public static String fromInvisibleLore(String lore){
        return lore.replaceAll(String.valueOf(COLOR_CHAR), "");
    }

}
