package wg.omnipotentialchests.chests.omnipotentialchests.engine.models;

import lombok.*;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.ListView;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class TreasureChest {

    @Getter
    @Setter
    private String name = "";
    private List<TreasureItem> treasureItems = new ArrayList<>();

    @SneakyThrows
    public TreasureItem getLowestChanceItem(){
        return treasureItems.stream().min(Comparator.comparingDouble(TreasureItem::getChance))
                .orElseThrow((Supplier<Throwable>) () -> new RuntimeException("No item in TreasureChest exception"));
    }

    public void setTreasureItems(Collection<TreasureItem> list){
        this.treasureItems = list.stream().sorted(Comparator.comparingDouble(TreasureItem::getChance).reversed())
                .collect(Collectors.toList());
    }

    public List<TreasureItem> getTreasureItems() {
        return Collections.unmodifiableList(treasureItems);
    }
}
