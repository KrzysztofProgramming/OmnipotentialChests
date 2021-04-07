package wg.omnipotentialchests.chests.omnipotentialchests.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.commands.*;

import java.util.List;
import java.util.stream.Collectors;

public class CommandsManager {

    public static List<String> mergeTabCompleter(List<String> currentTabCompleter, String arg) {
        final String lowArg = arg.toLowerCase();
        return currentTabCompleter.stream().filter(s -> s.toLowerCase().indexOf(lowArg) == 0).collect(Collectors.toList());
    }

    public static String getDescription(String label, Command command) {
        String[] strings = command.getUsage().split(" ", 2);
        String usage = strings.length < 2 ? "" : " " + command.getUsage().split(" ", 2)[1];
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Use" + ChatColor.GRAY + " => " + ChatColor.GREEN + "" + ChatColor.BOLD + "/" + label + ChatColor.GRAY + usage;
    }

    public void init() {
        new OmniChestsCommand(OmnipotentialChests.getInstance().getCommand("omnichests"));
        new GetChestCommand(OmnipotentialChests.getInstance().getCommand("getchest"));
        new EditChestCommand(OmnipotentialChests.getInstance().getCommand("editchest"));
        new CreateChestCommand(OmnipotentialChests.getInstance().getCommand("createchest"));
        new RemoveChestCommand(OmnipotentialChests.getInstance().getCommand("removechest"));
        new RenameCommand(OmnipotentialChests.getInstance().getCommand("rename"));
    }
}
