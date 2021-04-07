package wg.omnipotentialchests.chests.omnipotentialchests.engine.creators;

import ad.guis.ultimateguis.Colors;
import ad.guis.ultimateguis.engine.basics.BasicGui;
import ad.guis.ultimateguis.engine.basics.ModifiableGui;
import ad.guis.ultimateguis.engine.interfaces.Action;
import ad.guis.ultimateguis.examples.ConfirmGui;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.ChestsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.chat.ChatManager;

import java.util.*;
import java.util.stream.Collectors;

public class ChestCreatorGui extends ModifiableGui {


    public final static String CANCEL_PHRASE = "###";
    public final static int FIRST_UNMODIFIABLE_SLOT = 45;
    private final String chestName;
    private final ChestsManager chestsManager;
    private final ChatManager chatManager;
    private Map<ItemStack, Double> currentChances = new HashMap<>();
    private final List<ItemStack> currentItems = new ArrayList<>();
    private boolean allowClosed = false;


    public ChestCreatorGui(String chestName, BasicGui previousGui) throws IllegalArgumentException {
        super(6, chestName, previousGui);
        this.chestName = chestName;
        this.chestsManager = OmnipotentialChests.getInstance().getListenersManager().getChestsManager();
        this.chatManager = OmnipotentialChests.getInstance().getListenersManager().getChatManager();
        this.init(true);
    }

    public ChestCreatorGui(TreasureChest chest, BasicGui previousGui) throws IllegalArgumentException {
        super(6, chest.getName(), previousGui);
        this.chestName = chest.getName();
        this.chestsManager = OmnipotentialChests.getInstance().getListenersManager().getChestsManager();
        this.chatManager = OmnipotentialChests.getInstance().getListenersManager().getChatManager();
        this.setTreasureItems(chest.getTreasureItems());
        this.init(false);
    }

    public static String getItemName(ItemStack item) {
        return item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name();
    }

    private void setTreasureItems(List<TreasureItem> items) {
        this.currentChances = items.stream().collect(Collectors.toMap(TreasureItem::getItem, TreasureItem::getChance));
        this.setItems(items.stream().map(TreasureItem::getItem).collect(Collectors.toList()));
    }

    private void setItems(List<ItemStack> items) {
        for (int i = 0; i < FIRST_UNMODIFIABLE_SLOT && i < items.size(); i++) {
            getGui().setItem(i, items.get(i));
        }
    }

    public void init(boolean itemsModifiable) {
        ItemStack background = BasicGui.createBackground(Colors.GRAY);
        ItemStack resetChanceItem = BasicGui.createItem(Material.GLASS, OmnipotentialChests
                .convertColors("&eReset chances"));
        ItemStack copyChestItem = BasicGui.createItem(Material.PAPER, OmnipotentialChests.convertColors(
                "&d&lCopy chest"), BasicGui.splitLoreWithConversion(
                "&fCreate new chest and start editing with current items", 30));

        ItemStack saveItems = BasicGui.createItem(Material.INK_SACK, OmnipotentialChests
                .convertColors("&a&lsave"), DyeColor.LIME.getDyeData());

        ItemStack removeChestItem = BasicGui.createItem(Material.BARRIER, OmnipotentialChests.convertColors(
                "&c&lRemove chest"));

        ItemStack sortItem = BasicGui.createItem(Material.BOOKSHELF, OmnipotentialChests.convertColors(
                "&9Sort items"));

        this.setItem(0, 5, copyChestItem, this::onCopyChestClick);
        this.setItem(1, 5, background, null);
        this.setItem(2, 5, resetChanceItem, player -> this.resetChances());
        this.setItem(3, 5, sortItem, player -> this.sortItems());
        //inserting item
        this.setItem(5, 5, saveItems, this::saveChest);
        this.setItem(6, 5, background, null);

        this.setItem(7, 5, removeChestItem, this::onRemoveChestClick);
        this.setItem(8, 5, BasicGui.createBackItem(
                OmnipotentialChests.convertColors("&6Back")), this::backOrClose);

        this.switchMode(itemsModifiable);
    }

    public void sortItems() {
        this.setItems(getCurrentTreasureItems().stream()
                .sorted(Comparator.comparingDouble(TreasureItem::getChance))
                .map(TreasureItem::getItem).collect(Collectors.toList()));
    }

    private void onRemoveChestClick(Player p) {
        TreasureChest chest = chestsManager.getTreasureChest(this.chestName);
        if (chest == null) {
            p.sendMessage(OmnipotentialChests.convertColors("&cThis chest hasn't been saved yet"));
            return;
        }
        this.allowClosed = true;
        new ConfirmGui(OmnipotentialChests.convertColors("&dRemove this chest?"), player -> {
            chestsManager.removeChest(chestName);
            player.sendMessage(OmnipotentialChests.convertColors("&aSuccessfully removed chest!"));
            backOrClose(player);
        }, this::open, true).open(p);
    }

    private void onCopyChestClick(Player p) {
        this.allowClosed = true;
        p.closeInventory();
        p.sendMessage(OmnipotentialChests.convertColors("&aEnter new chest's name, &r"
                + CANCEL_PHRASE + "&a to cancel"));
        this.chatManager.setTask(p.getUniqueId(), this::playerChangeNameAction);
    }

    private boolean playerChangeNameAction(String message, Player player) {
        this.allowClosed = false;
        TreasureChest chest = this.chestsManager.getTreasureChest(message);
        if (chest != null && !chestName.equals(message)) {
            player.sendMessage(OmnipotentialChests.convertColors("&cChest with that name already exists"));
            return false;
        }
        TreasureChest currentChest = this.getTreasureChest();
        currentChest.setName(OmnipotentialChests.convertColors(message));
        new ChestCreatorGui(currentChest, this.previousGui).open(player);
        return true;
    }

    public void resetChances() {
        this.currentChances.clear();
        if (!this.isInsertingMode()) {
            this.showAllPercentValues();
        }
    }

    public void setModeSwitchItem(boolean isInserting) {
        ItemStack item;
        if (isInserting) {
            item = BasicGui.createItem(Material.REDSTONE, OmnipotentialChests.convertColors("&6&lMode"),
                    BasicGui.simpleSplitLore(OmnipotentialChests.convertColors(
                            "&fcurrent: &a&linserting items"),
                            OmnipotentialChests.convertColors("&bClick to switch mode")));
        } else {
            item = BasicGui.createItem(Material.BOOK, OmnipotentialChests.convertColors("&6&lMode"),
                    BasicGui.simpleSplitLore(OmnipotentialChests.convertColors(
                            "&fcurrent: &asetting chances"),
                            OmnipotentialChests.convertColors("&bClick to switch mode")));
        }

        this.setItem(4, 5, item, player -> switchMode(!isInserting));
    }

    public void switchMode(boolean isInserting) {
        this.setModeSwitchItem(isInserting);
        setItemsModifiable(isInserting);
        if (!isInserting) {
            this.readItemsFromInventory();
            this.showAllPercentValues();
        } else {
            this.hideAllPercentValues();
        }
    }

    private void showAllPercentValues() {
        for (int i = 0; i < FIRST_UNMODIFIABLE_SLOT; i++) {
            try {
                PercentManager.showPercentValue(this.getGui().getItem(i), this.currentChances.get(this.getGui().getItem(i)));
            } catch (NullPointerException ignore) {
                PercentManager.showPercentValue(this.getGui().getItem(i), 0);
            }
        }
    }

    private void hideAllPercentValues() {
        for (int i = 0; i < FIRST_UNMODIFIABLE_SLOT; i++) {
            try {
                PercentManager.removePercentFromLore(this.getGui().getItem(i));
            } catch (NullPointerException ignore) {
                PercentManager.removePercentFromLore(this.getGui().getItem(i));
            }
        }
    }

    public void saveChest(Player p) {
        if (this.isInsertingMode()) {
            p.sendMessage(OmnipotentialChests.convertColors("&dSwitch mode first"));
            return;
        }
        if (!hasAllItemsPercent()) {
            p.sendMessage(OmnipotentialChests.convertColors("&cEach item must have set chance"));
            return;
        }
        double sum = getSumChances();
        if (getSumChances() != 100) {
            p.sendMessage(OmnipotentialChests.convertColors("&cSum of chances must be equal to 100," +
                    " there's still &r&l" + (100 - sum) + "%&r&c to set"));
            return;
        }
        this.allowClosed = true;
        new ConfirmGui(OmnipotentialChests.convertColors("&dAre you sure?"), player -> {
            chestsManager.setChest(getTreasureChest());
            player.sendMessage(OmnipotentialChests.convertColors("&aChest saved!"));
            ChestCreatorGui.this.backOrClose(player);
        }, this::open).open(p);
        this.allowClosed = false;
    }

    private boolean isProgressSaved() {
        TreasureChest savedChest = chestsManager.getTreasureChest(this.chestName);
        return this.getTreasureChest().equals(savedChest);
    }

    private void setItemsModifiable(boolean value) {
        if (value) this.setFirstUnmodifiableSlot(FIRST_UNMODIFIABLE_SLOT);
        else this.setFirstUnmodifiableSlot(0);
    }

    public boolean isInsertingMode() {
        return this.getFirstUnmodifiableSlot() > 0;
    }

    private void readItemsFromInventory() {
        this.currentItems.clear();
        for (int i = 0; i < FIRST_UNMODIFIABLE_SLOT; i++) {
            ItemStack item = this.getGui().getItem(i);
            if (item == null || item.getItemMeta() == null)
                continue;
            if (this.currentItems.contains(item)) {
                this.getGui().setItem(i, null);
                this.returnItemToPlayer(item); //todo fix this
                continue;
            }
            this.currentItems.add(item);
        }
        this.currentChances.keySet().removeIf(value -> !currentItems.contains(value));
    }

    public TreasureChest getTreasureChest() {
        return new TreasureChest(chestName, this.getCurrentTreasureItems());

    }

    private List<TreasureItem> getCurrentTreasureItems() {
        return this.currentItems.stream().filter(Objects::nonNull)
                .map(this::getTreasureItem).collect(Collectors.toList());
    }

    private TreasureItem getTreasureItem(ItemStack item) {
        try {
            double chance = this.currentChances.get(PercentManager.removePercentValueAndCopy(item));
            return new TreasureItem(PercentManager.removePercentValueAndCopy(item), chance);
        } catch (NullPointerException ignore) {
        }
        return new TreasureItem(item, 0);
    }

    @Override
    public void onClose() {
        if (!this.isProgressSaved() && !allowClosed) {
            ConfirmGui confirmGui = new ConfirmGui(OmnipotentialChests.convertColors("&bExit without saving?"),
                    this::backOrClose, this::open, true);
            confirmGui.open(this.getLastViewer());
        }
        super.onClose();
    }

    @Override
    protected boolean advancedClickHandler(InventoryClickEvent e, Action defaultAction) {
        if (isInsertingMode() || e.getRawSlot() >= FIRST_UNMODIFIABLE_SLOT || e.getCurrentItem() == null
                || e.getCurrentItem().getItemMeta() == null)
            return super.advancedClickHandler(e, defaultAction);

        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (this.getSumChances() - getItemChance(clickedItem) >= 100) {
            e.getWhoClicked().sendMessage(OmnipotentialChests.convertColors("&cAlready have &l100%&r&c," +
                    " edit another item chance first"));
            return false;
        }
        chatManager.setTask(e.getWhoClicked().getUniqueId(), (chatMessage, player)
                -> chatChanceAction(chatMessage, player, e.getRawSlot(), e.getCurrentItem()));

        this.allowClosed = true;
        this.closeLater();
        e.getWhoClicked().sendMessage(OmnipotentialChests.convertColors("&aEnter chance in percent for: &r")
                + getItemName(clickedItem) + OmnipotentialChests.convertColors("&r&a max: &l")
                + (100 - getSumChances() + getItemChance(clickedItem)) + OmnipotentialChests.convertColors(
                "&a or cancel with: &r" + CANCEL_PHRASE
        ));
        return false;
    }

    private double getItemChance(ItemStack item) {
        try {
            return this.currentChances.get(PercentManager.removePercentValueAndCopy(item));
        } catch (NullPointerException e) {
            return 0;
        }
    }

    private boolean chatChanceAction(String chatMessage, Player player, int rawSlot, ItemStack item) {
        this.allowClosed = false;
        if (chatMessage.equals(CANCEL_PHRASE)) {
            this.open(player);
            return true;
        }
        double chance;
        try {
            chance = Double.parseDouble(chatMessage);
        } catch (NumberFormatException e) {
            player.sendMessage(OmnipotentialChests.convertColors(
                    "Wrong number format, try again, or cancel with: &r") + CANCEL_PHRASE);
            return false;
        }
        String error = validateChance(chance, item);
        if (error.equals("")) {
            this.currentChances.put(PercentManager.removePercentValueAndCopy(item), chance);
            PercentManager.showPercentValue(item, chance);
            this.open(player);
            return true;
        }
        player.sendMessage(ChatColor.RED + error);
        return false;

    }


    private boolean hasAllItemsPercent() {
        return !this.currentItems.isEmpty() && this.currentItems.stream().allMatch(item -> {
            try {
                return getItemChance(item) > 0;
            } catch (NullPointerException e) {
                return false;
            }
        });
    }


    private String validateChance(double chance, ItemStack item) {
        double sum = getSumChances();
        double currentItemChance = getItemChance(item);
        if (chance + sum - currentItemChance > 100)
            return OmnipotentialChests.convertColors("&cSum of chances can't exceed 100, &f&l"
                    + (100 - sum + currentItemChance) + "&r&c is max");
        if (chance <= 0)
            return OmnipotentialChests.convertColors("&cChance must be greater than 0");
        return "";
    }

    private double getSumChances() {
        return this.currentChances.values().stream().reduce(Double::sum).orElse((double) 0);
    }


}
