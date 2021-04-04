package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.ChestsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.creators.ChestCreatorGui;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.CommandsManager;

public class CreateChestCommand implements CommandExecutor {

    private final ChestsManager chestsManager;

    public CreateChestCommand(PluginCommand command) {
        command.setExecutor(this);
        this.chestsManager = OmnipotentialChests.getInstance().getListenersManager().getChestsManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(OmnipotentialChests.convertColors("&cOnly player can execute this command"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(CommandsManager.getDescription(label, command));
            return true;
        }
        String chestName = String.join(" ", args);
        TreasureChest chest = chestsManager.getTreasureChest(chestName);
        if (chest != null) {
            sender.sendMessage(OmnipotentialChests.convertColors("&cChest with that name already exists"));
            return true;
        }
        new ChestCreatorGui(OmnipotentialChests.convertColors(chestName), null).open((Player) sender);
        return true;
    }
}
