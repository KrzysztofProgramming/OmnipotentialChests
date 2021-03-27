package wg.omnipotentialchests.chests.omnipotentialchests.engine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSONTreasureItem {

    @Expose
    @SerializedName(value = "item")
    private String base64ItemStack;
    @Expose
    @SerializedName(value = "chance")
    private double chance;
}
