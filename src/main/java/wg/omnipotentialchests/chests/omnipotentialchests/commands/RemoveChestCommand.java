package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;

public class RemoveChestCommand extends ChestCommand {

    public RemoveChestCommand(PluginCommand command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TreasureChest chest = this.getChestWithValidation(sender, command, label, args);
        if (chest == null) return true;
        this.chestsManager.removeChest(OmnipotentialChests.convertColors(args[0]));
        sender.sendMessage(OmnipotentialChests.convertColors("&aSuccessfully removed chest"));
        return true;
    }
}
