package wg.omnipotentialchests.chests.omnipotentialchests.managers.chat;

import org.bukkit.entity.Player;

public interface PlayerChatAction {
    boolean action(String chatMessage, Player player);
}
