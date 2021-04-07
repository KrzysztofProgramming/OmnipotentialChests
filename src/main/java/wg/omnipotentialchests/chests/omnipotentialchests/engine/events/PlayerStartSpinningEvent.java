package wg.omnipotentialchests.chests.omnipotentialchests.engine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.spinning.ChestGui;

public class PlayerStartSpinningEvent extends PlayerSpinningEvent {
    private final static HandlerList handlerList = new HandlerList();

    public PlayerStartSpinningEvent(Player who, ChestGui gui) {
        super(who, gui);
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
