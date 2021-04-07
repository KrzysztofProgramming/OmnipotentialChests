package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;

public class RenameCommand implements CommandExecutor {
    public RenameCommand(PluginCommand command) {
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(OmnipotentialChests.convertColors("&cOnly player can execute this command"));
            return true;
        }
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item == null || item.getItemMeta() == null){
            player.sendMessage(OmnipotentialChests.convertColors("&cYou must held item in your main hand!"));
            return true;
        }
        String newName = args.length > 0 ? String.join(" ", args) : "";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(OmnipotentialChests.convertColors(newName));
        item.setItemMeta(meta);
        return true;
    }
}
