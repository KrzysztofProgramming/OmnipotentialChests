package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class OmniChestsCommand implements CommandExecutor {

    public OmniChestsCommand(PluginCommand command){
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only player can execute this command");
            return true;
        }
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
//        OmnipotentialChests.getInstance().getConfigsManager().ymlGenerator
//                .generateYml("testowy", "", "");
//        try {
//        File file = new File(OmnipotentialChests.getInstance().getDataFolder().getCanonicalPath()
//                + File.separator + "elo.bin");
//            if (!(file.getParentFile().mkdir()
//                    && file.createNewFile())) {
//                player.sendMessage("Can't create a file");
//            }
//            OmnipotentialChests.getInstance().getConfig().set("item.elo", item);
//            OmnipotentialChests.getInstance().getConfig().save(file);
//            ItemStack getItem = (ItemStack) OmnipotentialChests.getInstance().getConfig().get("item.elo");
//            player.getInventory().addItem(getItem);
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
        player.sendMessage("git");
        return true;
    }
}
