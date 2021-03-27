package wg.omnipotentialchests.chests.omnipotentialchests.engine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSONTreasureChest {

    @Expose
    @SerializedName(value = "name")
    private String name;
    @Expose
    @SerializedName(value = "treasureItems")
    private List<JSONTreasureItem> treasureItems = new ArrayList<>();
}
