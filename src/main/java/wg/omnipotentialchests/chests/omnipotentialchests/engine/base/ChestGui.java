package wg.omnipotentialchests.chests.omnipotentialchests.engine.base;

import ad.guis.ultimateguis.Colors;
import ad.guis.ultimateguis.engine.basics.BasicGui;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerFinishedSpinningEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerStartSpinningEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;
import java.util.*;


public class ChestGui extends BasicGui {

    public final static int ESTIMATED_ITEM_COUNT = 50; //may be bigger but not lower
    public final static int MINIMUM_SPIN_POSITION = 20;

    @Getter
    private TreasureChest treasureChest;
    private List<TreasureItem> expandedItemsList = new ArrayList<>(ESTIMATED_ITEM_COUNT);
    private final Random randomGenerator = new Random();
    private int currentShift;

    public ChestGui(TreasureChest treasureChest, @Nullable BasicGui previousGui) {
        super(4, treasureChest.getName(), previousGui);
        this.treasureChest = treasureChest;
        this.init();
    }
    public ChestGui(TreasureChest treasureChest) {
        this(treasureChest, null);
    }


    private void init(){
        ItemStack backgroundOrange = BasicGui.createBackground(Colors.ORANGE);
        ItemStack backgroundGreen = BasicGui.createBackground(Colors.GREEN);

        this.initModifiedList();
        this.fillTreasureItems(this.expandedItemsList);
        this.currentShift = 0;


        this.setItem(4,0, backgroundGreen, null);
        this.setItem(4,2, backgroundGreen, null);
        this.autoFill(backgroundOrange);
    }


    private void initModifiedList(){
        for(TreasureItem item: treasureChest.getTreasureItems()){
            int itemCount = atLeastOne(ESTIMATED_ITEM_COUNT * item.getChance()/100);
            this.insertNTimes(item, itemCount);
        }
        Collections.shuffle(this.expandedItemsList);

    }

    private int atLeastOne(double value){
        if(value <= 1) return 1;
        return (int) value;
    }

    private void insertNTimes(TreasureItem item, int n){
        for(int i = 0; i<n; i++) {
            this.expandedItemsList.add(item);
        }
    }

    private void fillTreasureItems(List<TreasureItem> items){
        for(int i=0; i< 9; i++) {
            this.setItem(i, 1, items.get(i).getItem(), null);
        }
    }

    private TreasureItem randomizeItem(){
        List<TreasureItem> items = this.treasureChest.getTreasureItems();
        double random = randomGenerator.nextDouble() * 100;
        double currentPercent = 0;
        for (TreasureItem item : items) {
            if (random <= currentPercent + item.getChance()) {
                return item;
            }
            currentPercent += item.getChance();
        }
        return items.get(items.size() - 1);
    }

    public void startSpinning(){
        Bukkit.getPluginManager().callEvent(new PlayerStartSpinningEvent(this.getLastViewer(), this));
    }

    void startSpinningWithoutEvent(){
        TreasureItem reward = randomizeItem();
        List<TreasureItem> shiftedItemsList = new ArrayList<>(this.expandedItemsList);
        int index = shiftedItemsList.indexOf(reward);
        int indexAfterMinSpins = (MINIMUM_SPIN_POSITION + 4) % shiftedItemsList.size();
        int additionalSpins = (shiftedItemsList.size() + index + 1 - indexAfterMinSpins) % shiftedItemsList.size();
        final int totalSpins = MINIMUM_SPIN_POSITION + additionalSpins;
        final int[] spinsLeft = {totalSpins};
        Bukkit.broadcastMessage(reward.getItem().getItemMeta().getDisplayName());

        Bukkit.broadcastMessage(String.valueOf(spinsLeft[0]));
        Bukkit.getScheduler().scheduleSyncDelayedTask(OmnipotentialChests.getInstance(), new Runnable() {
            @Override
            public void run() {
                shiftListLeft(shiftedItemsList);
                fillTreasureItems(shiftedItemsList);
                spinsLeft[0]--;
                if(spinsLeft[0] <= 0){
                    Bukkit.getPluginManager().callEvent(new PlayerFinishedSpinningEvent(
                            ChestGui.this.getLastViewer(), ChestGui.this, reward));
                    return;
                }
                if(!isOpen()){
                    ChestGui.this.open(getLastViewer());
                    currentShift = totalSpins;
                    shiftListLeft(shiftedItemsList);
                    Bukkit.getPluginManager().callEvent(new PlayerFinishedSpinningEvent(
                            ChestGui.this.getLastViewer(), ChestGui.this, reward));
                    return;
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(OmnipotentialChests.getInstance(), this,
                        Math.max(20/spinsLeft[0], 1));
            }
        }, 0);

    }

    private void shiftListLeft(List<TreasureItem> list){
        for(int i = 0; i<list.size(); i++){
            list.set(i, this.expandedItemsList.get((currentShift + i)%list.size()));
        }
        currentShift++;
        Bukkit.broadcastMessage(String.valueOf(currentShift));
    }

    public void resetSpinning(){
        this.init();
    }


}
