package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.creators.ChestCreatorGui;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;

public class EditChestCommand extends ChestCommand {
    public EditChestCommand(PluginCommand command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(OmnipotentialChests.convertColors("&cOnly player can execute this command"));
            return true;
        }
        TreasureChest chest = this.getChestWithValidation(sender, command, label, args);
        if(chest == null) return true;
        Player player = (Player)sender;
        new ChestCreatorGui(chest, null).open(player);
        return true;
    }
}
