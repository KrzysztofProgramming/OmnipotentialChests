package wg.omnipotentialchests.chests.omnipotentialchests.engine.guis;

import ad.guis.ultimateguis.Colors;
import ad.guis.ultimateguis.engine.basics.BasicGui;
import com.sun.istack.internal.Nullable;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;

import java.util.*;
import java.util.stream.Collectors;


public class ChestGui extends BasicGui {

    private TreasureChest treasureChest;
    private List<TreasureItem> modifyItemsList = new ArrayList<>(MAX_ITEM_COUNT);
    private int currentShift = 0;
    private int itemsInGui = 9;
    public final static int MAX_ITEM_COUNT = 100;

    public ChestGui(TreasureChest treasureChest, @Nullable BasicGui previousGui) {
        super(4, treasureChest.getName(), previousGui);
        this.treasureChest = treasureChest;
        this.getLastViewer();
    }


    private void initModifiedList(){
        double lowestChance = treasureChest.getLowestChanceItem().getChance();
        int itemsLeft = MAX_ITEM_COUNT;
        double percentLeft = 100;
        int countToInsert = 0;
        for(int i=0; i<treasureChest.getTreasureItems().size(); i++){
//            countToInsert = atLeastOne((treasureChest.getTreasureItems().get(i).getChance()/100));
//
//
//
//            this.insertNTimes(atLeastOne((treasureChest.getTreasureItems().get(i).getChance()/100),
//                    treasureChest.getTreasureItems().get(i));
        }

    }

    private int atLeastOne(double value){
        if(value <= 1) return 1;
        return (int) value;
    }

    private void insertNTimes(int n, TreasureItem item){
        for(int i = 0; i<n; i++) {
            this.modifyItemsList.add(item);
        }
    }

    private void init(){
        ItemStack backgroundOrange = BasicGui.createBackground(Colors.ORANGE);
        ItemStack backgroundGreen = BasicGui.createBackground(Colors.GREEN);
        this.setItem(4,0, backgroundGreen, null);
        this.setItem(4,2, backgroundGreen, null);
        this.fillTreasureItems();
    }

    private void fillTreasureItems(){
        for(int i=0; i< 9; i++) {
            this.setItem(i, 1, this.modifyItemsList.get(i).getItem(), null);
        }
    }

    public void startSpinning(){

    }
}
