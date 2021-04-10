package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.ChestsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.creators.ChestsListGui;

public class OmniChestsCommand implements CommandExecutor {

    private final ChestsManager chestsManager;

    public OmniChestsCommand(PluginCommand command) {
        command.setExecutor(this);
        this.chestsManager = OmnipotentialChests.getInstance().getListenersManager().getChestsManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only player can execute this command");
            return true;
        }
        Player player = (Player) sender;
        ItemStack item = new ItemStack(Material.ANVIL);
        ChestsListGui gui = new ChestsListGui(null);
        gui.open(player);
        return true;
    }
}
