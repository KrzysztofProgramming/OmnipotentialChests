package wg.omnipotentialchests.chests.omnipotentialchests.engine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.guis.ChestGui;

public class PlayerStartSpinningEvent extends PlayerSpinningEvent {
    private final static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public PlayerStartSpinningEvent(Player who, ChestGui gui) {
        super(who, gui);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}