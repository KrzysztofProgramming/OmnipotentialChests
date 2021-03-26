package wg.omnipotentialchests.chests.omnipotentialchests.engine.models;

import ad.guis.ultimateguis.engine.basics.BasicGui;
import lombok.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;

import javax.swing.text.html.ListView;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TreasureChest {

    @Getter
    @Setter
    private String name = "";
    private List<TreasureItem> treasureItems = new ArrayList<>();

    public TreasureChest(String name, List<TreasureItem> treasureItems){
        this.name = name;
        this.setTreasureItems(treasureItems);
    }

    @SneakyThrows
    public TreasureItem getLowestChanceItem(){
        return treasureItems.stream().min(Comparator.comparingDouble(TreasureItem::getChance))
                .orElseThrow((Supplier<Throwable>) () -> new RuntimeException("No item in TreasureChest exception"));
    }

    public void setTreasureItems(Collection<TreasureItem> list){
        this.treasureItems = list.stream().sorted(Comparator.comparingDouble(TreasureItem::getChance))
                .collect(Collectors.toList());
    }

    public List<TreasureItem> getTreasureItems() {
        return Collections.unmodifiableList(treasureItems);
    }


    public static TreasureChest getExample(){
        ItemStack netherStar = BasicGui.createItem(Material.NETHER_STAR, "Star of omnipotential power");
        ItemStack dragonEgg = BasicGui.createItem(Material.DRAGON_EGG, "Mysterious egg");
        ItemStack dirt = BasicGui.createItem(Material.DIRT, "Dirt");
        ItemStack diamond = BasicGui.createItem(Material.DIAMOND, "Kosztowny klejnot");
        ItemStack meat = BasicGui.createItem(Material.COOKED_BEEF, "Mięsko");
        ItemStack leggings = BasicGui.createItem(Material.LEATHER_LEGGINGS, "Spodnie");
        ItemStack sword = BasicGui.createItem(Material.IRON_SWORD, "Żelazny miecz");

        List<TreasureItem> items = new ArrayList<>();
        items.add(new TreasureItem(netherStar,1));
        items.add(new TreasureItem(dragonEgg, 5));
        items.add(new TreasureItem( diamond, 9));
        items.add(new TreasureItem(meat, 10));
        items.add(new TreasureItem(dirt, 55));
        items.add(new TreasureItem(leggings, 5));
        items.add(new TreasureItem(sword, 5));
        return new TreasureChest("Example chest", items);
    }
}
