package wg.omnipotentialchests.chests.omnipotentialchests.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.events.PlayerStartSpinningEvent;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.base.ChestGui;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureChest;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.models.TreasureItem;

import java.util.ArrayList;
import java.util.List;

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


        ItemStack itemStack = player.getInventory().getItemInMainHand();
        TreasureChest treasureChest = new TreasureChest();
        treasureChest.setName("elo212213123123");
        List<TreasureItem> list = new ArrayList<>();
        System.out.println(itemStack);
        TreasureItem treasureItem = new TreasureItem(itemStack,29.9);
        list.add(treasureItem);
        list.add(treasureItem);
        treasureChest.setTreasureItems(list);


      //  OmnipotentialChests.getInstance().getConfigsManager().JSONGenerator.generateJSONFile("l1");
        OmnipotentialChests.getInstance().getConfigsManager().JSONGenerator.addObjectToExistingFile("l1", "elo", treasureChest);


//        Bukkit.broadcastMessage(String.valueOf(OmnipotentialChests.getInstance().getDataFolder().mkdir()));
//        Bukkit.getPluginManager().callEvent(new PlayerStartSpinningEvent(player,
//                new ChestGui(TreasureChest.getExample())));

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
