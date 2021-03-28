package wg.omnipotentialchests.chests.omnipotentialchests.engine.base;

import ad.guis.ultimateguis.Colors;
import ad.guis.ultimateguis.engine.basics.BasicGui;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerFinishedSpinningEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerStartSpinningEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class ChestGui extends BasicGui {

    public final static int ESTIMATED_ITEM_COUNT = 50; //may be bigger but not lower
    public final static int MINIMUM_SPIN_POSITION = 20;
    private final Random randomGenerator = new Random();
    @Getter
    private final TreasureChest treasureChest;
    private final List<TreasureItem> expandedItemsList = new ArrayList<>(ESTIMATED_ITEM_COUNT);
    private List<TreasureItem> shiftedItemsList = new ArrayList<>(ESTIMATED_ITEM_COUNT);
    private int currentShift = 0;
    private int currentTotalSpins = 0;
    private TreasureItem currentReward = null;

    public ChestGui(TreasureChest treasureChest, BasicGui previousGui) {
        super(4, treasureChest.getName(), previousGui);
        this.treasureChest = treasureChest;
        this.init();
    }

    public ChestGui(TreasureChest treasureChest) {
        this(treasureChest, null);
    }

    public boolean isDuringSpinning(){
        return currentTotalSpins > 0;
    }

    private void init() {
        ItemStack backgroundOrange = BasicGui.createBackground(Colors.ORANGE);
        ItemStack backgroundGreen = BasicGui.createBackground(Colors.GREEN);

        this.initModifiedList();
        this.fillTreasureItems(this.expandedItemsList);
        this.currentShift = 0;


        this.setItem(4, 0, backgroundGreen, null);
        this.setItem(4, 2, backgroundGreen, null);
        this.autoFill(backgroundOrange);
    }


    private void initModifiedList() {
        for (TreasureItem item : treasureChest.getTreasureItems()) {
            int itemCount = atLeastOne(ESTIMATED_ITEM_COUNT * item.getChance() / 100);
            this.insertNTimes(item, itemCount);
        }
        Collections.shuffle(this.expandedItemsList);

    }

    private int atLeastOne(double value) {
        if (value <= 1) return 1;
        return (int) value;
    }

    private void insertNTimes(TreasureItem item, int n) {
        for (int i = 0; i < n; i++) {
            this.expandedItemsList.add(item);
        }
    }

    private void fillTreasureItems(List<TreasureItem> items) {
        for (int i = 0; i < 9; i++) {
            this.setItem(i, 1, items.get(i).getItem(), null);
        }
    }

    private TreasureItem randomizeItem() {
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

    public void startSpinning(Player player) {
        Bukkit.getPluginManager().callEvent(new PlayerStartSpinningEvent(player, this));
    }

    void startSpinningWithoutEvent() {
        currentReward = randomizeItem();
        shiftedItemsList = new ArrayList<>(this.expandedItemsList);
        int index = shiftedItemsList.indexOf(currentReward);
        int indexAfterMinSpins = (MINIMUM_SPIN_POSITION + 4) % shiftedItemsList.size();
        int additionalSpins = (shiftedItemsList.size() + index - indexAfterMinSpins) % shiftedItemsList.size();
        this.currentTotalSpins = MINIMUM_SPIN_POSITION + additionalSpins;
        final int[] spinsLeft = {this.currentTotalSpins};

        Bukkit.getScheduler().scheduleSyncDelayedTask(OmnipotentialChests.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(currentTotalSpins <=0 ) return;

                shiftList();
                fillTreasureItems(shiftedItemsList);

                if (spinsLeft[0] <= 0) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(OmnipotentialChests.getInstance(),
                            ChestGui.this::callFinishedEvent, 20);
                    return;
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(OmnipotentialChests.getInstance(), this,
                        Math.max(20 / spinsLeft[0], 1));

                spinsLeft[0]--;
            }
        }, 20);
    }

    @Override
    public void onClose() {
        if(!this.isDuringSpinning()) return;
        this.skipToReward();
        this.open(this.getLastViewer());
        callFinishedEvent();
        super.onClose();
    }

    private void callFinishedEvent(){
        this.currentTotalSpins = 0;
        Bukkit.getPluginManager().callEvent(new PlayerFinishedSpinningEvent(
                ChestGui.this.getLastViewer(), ChestGui.this, currentReward));
    }

    void setRewardBackground(){
        ItemStack backgroundGreen = BasicGui.createBackground(Colors.GREEN);
        ItemStack backgroundOrange = BasicGui.createBackground(Colors.ORANGE);
        this.gui.clear();

        this.setItem(4, 0, backgroundOrange, null);
        this.setItem(4, 2, backgroundOrange, null);
        this.fillTreasureItems(this.shiftedItemsList);
        this.autoFill(backgroundGreen);
    }

    private void skipToReward(){
        this.currentShift = this.currentTotalSpins;
        this.shiftList();
        this.fillTreasureItems(this.shiftedItemsList);
    }

    private void shiftList() {
        for (int i = 0; i < shiftedItemsList.size(); i++) {
            shiftedItemsList.set(i, this.expandedItemsList.get((currentShift + i) % shiftedItemsList.size()));
        }
        currentShift++;
    }

    public void resetSpinning() {
        this.init();
    }


}
