package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.command.*;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.ChestsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.CommandsManager;

import java.util.ArrayList;
import java.util.List;

public abstract class ChestCommand implements CommandExecutor, TabCompleter {

    protected final ChestsManager chestsManager;

    public ChestCommand(PluginCommand command) {
        command.setTabCompleter(this);
        command.setExecutor(this);
        this.chestsManager = OmnipotentialChests.getInstance().getListenersManager().getChestsManager();
    }

    protected TreasureChest getChestWithValidation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CommandsManager.getDescription(label, command));
            return null;
        }
        TreasureChest chest = chestsManager.getTreasureChest(OmnipotentialChests.convertColors(String.join(" ", args)));
        if (chest == null) {
            sender.sendMessage(OmnipotentialChests.convertColors("&cThere's no chest with that name"));
            return null;
        }
        return chest;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) return new ArrayList<>(0);
        return CommandsManager.mergeTabCompleter(chestsManager.getTreasureChestsNames(), args[0]);
    }
}
