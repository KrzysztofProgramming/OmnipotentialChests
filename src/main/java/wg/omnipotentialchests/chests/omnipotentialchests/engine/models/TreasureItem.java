package wg.omnipotentialchests.chests.omnipotentialchests.engine.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class TreasureItem {
    private ItemStack item;
    private double chance;
}
