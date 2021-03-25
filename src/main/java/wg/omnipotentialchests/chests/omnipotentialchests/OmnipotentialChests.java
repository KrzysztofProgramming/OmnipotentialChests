package wg.omnipotentialchests.chests.omnipotentialchests;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.CommandsManager;

public final class OmnipotentialChests extends JavaPlugin {

    @Getter
    private static OmnipotentialChests instance;

    @Getter
    private CommandsManager commandsManager;

    public void createClasses(){
        this.commandsManager = new CommandsManager();
    }

    public void initClasses(){
        this.commandsManager.init();
    }

    @Override
    public void onEnable() {
        instance = this;
        createClasses();
        initClasses();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(instance==this)
            instance = null;
    }
}
