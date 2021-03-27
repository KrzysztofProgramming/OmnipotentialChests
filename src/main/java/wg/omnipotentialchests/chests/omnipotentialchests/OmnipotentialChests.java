package wg.omnipotentialchests.chests.omnipotentialchests;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import wg.omnipotentialchests.chests.omnipotentialchests.configs.ConfigsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.CommandsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.ListenersManager;


public final class OmnipotentialChests extends JavaPlugin {

    @Getter
    private static OmnipotentialChests instance;

    @Getter
    private ConfigsManager configsManager;

    @Getter
    private CommandsManager commandsManager;

    @Getter
    private ListenersManager listenersManager;

    public void createClasses() {
        this.configsManager = new ConfigsManager();
        this.listenersManager = new ListenersManager();
        this.commandsManager = new CommandsManager();
    }

    public void initClasses() {
        this.configsManager.init();
        this.listenersManager.init();
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
        if (instance == this)
            instance = null;
    }
}
