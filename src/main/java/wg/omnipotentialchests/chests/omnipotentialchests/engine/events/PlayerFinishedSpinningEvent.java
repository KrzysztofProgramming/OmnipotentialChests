package wg.omnipotentialchests.chests.omnipotentialchests.engine.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.base.ChestGui;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;

public class PlayerFinishedSpinningEvent extends PlayerSpinningEvent{

    private final static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Getter
    @Setter
    private TreasureItem rewardItem;

    public PlayerFinishedSpinningEvent(Player who, ChestGui gui, TreasureItem rewardItem) {
        super(who, gui);
        this.rewardItem = rewardItem;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
