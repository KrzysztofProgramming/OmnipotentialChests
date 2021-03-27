package wg.omnipotentialchests.chests.omnipotentialchests.engine.models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class TreasureItem {
    @Expose
    private ItemStack item;
    @Expose
    private double chance;
}
