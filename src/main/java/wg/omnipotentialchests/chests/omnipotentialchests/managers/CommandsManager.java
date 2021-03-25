package wg.omnipotentialchests.chests.omnipotentialchests.managers;

import wg.omnipotentialchests.chests.omnipotentialchests.OmnipotentialChests;
import wg.omnipotentialchests.chests.omnipotentialchests.commands.OmniChestsCommand;

public class CommandsManager {

    public void init(){
        new OmniChestsCommand(OmnipotentialChests.getInstance().getCommand("omnichests"));
    }
}
