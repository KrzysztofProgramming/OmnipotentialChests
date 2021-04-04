package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import ad.guis.ultimateguis.engine.basics.BasicGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.CommandsManager;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GetChestCommand extends ChestCommand {

    public GetChestCommand(PluginCommand command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(OmnipotentialChests.convertColors("&cOnly player can execute this command"));
            return true;
        }
        if(args.length < 2){
            sender.sendMessage(CommandsManager.getDescription(label, command));
            return true;
        }
        TreasureChest chest = this.getChestWithValidation(sender, command, label,
                Arrays.copyOfRange(args, 0, args.length - 1));

        if(chest == null) return true;
        Player player = (Player)sender;
        int count;
        try{
            count = Integer.parseInt(args[args.length - 1]);
            if(count <=0 ) count = 1;
        }
        catch (NumberFormatException e){
            player.sendMessage(OmnipotentialChests.convertColors("&cWrong number format"));
            return true;
        }
        ItemStack chestItem = chest.getChestItem();
        ItemStack keyItem = chest.getKeyItem();
        chestItem.setAmount(count);
        keyItem.setAmount(count);

        BasicGui.returnItemToPlayer(player, chestItem, keyItem);
        return true;
    }
}
