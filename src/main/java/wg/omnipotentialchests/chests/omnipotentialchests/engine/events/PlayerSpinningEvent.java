package wg.omnipotentialchests.chests.omnipotentialchests.engine.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.guis.ChestGui;

public abstract class PlayerSpinningEvent extends PlayerEvent implements Cancellable {
    @Getter
    protected ChestGui chestGui;

    private boolean cancel = false;

    public PlayerSpinningEvent(Player who, ChestGui gui) {
        super(who);
        this.chestGui = gui;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
