package wg.omnipotentialchests.chests.omnipotentialchests.managers.chat;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ChatManager implements Listener {
    private final Map<UUID, PlayerChatAction> chatTasks = new HashMap<>();

    public void init() {
        Bukkit.getServer().getPluginManager()
                .registerEvents(this, OmnipotentialChests.getInstance());
    }

    @EventHandler
    private void removeTaskOnLeave(PlayerQuitEvent event) {
        chatTasks.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW)
    private void executeTask(AsyncPlayerChatEvent event) {
        if (chatTasks.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(OmnipotentialChests.getInstance(), () -> {
                if (chatTasks.get(event.getPlayer().getUniqueId()).action(event.getMessage(), event.getPlayer()))
                    chatTasks.remove(event.getPlayer().getUniqueId());
            });
        }
    }

    public void removeTask(UUID playerUUID) {
        chatTasks.remove(playerUUID);
    }

    public void clearTasks() {
        chatTasks.clear();
    }

    public void setTask(UUID playerUUID, PlayerChatAction action) {
        chatTasks.put(playerUUID, action);
    }
}