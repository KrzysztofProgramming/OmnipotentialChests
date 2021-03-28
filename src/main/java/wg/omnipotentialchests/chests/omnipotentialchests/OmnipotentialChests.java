package wg.omnipotentialchests.chests.omnipotentialchests;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
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

    public static String convertColors(String st) {
        return ChatColor.translateAlternateColorCodes('&', st);
    }

    private String getMinecraftVersion(Server server) {
        String version = server.getVersion();
        int start = version.indexOf("MC: ") + 4;
        int end = version.length() - 1;
        return version.substring(start, end);
    }

    private String getPluginVersion() {
        PluginDescriptionFile pdf = this.getDescription();
        return pdf.getVersion();
    }

    private void enablingMessage() {
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("      &6,ad8888ba,      ,ad8888ba,   &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("     &6d8'      `8b    d8'       `8b &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &6d8'        `8b  d8'            &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &688          88  88             &e88aaaaaaaa88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &688          88  88             &e88\"\"\"\"\"\"\"\"88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &6Y8,        ,8P  Y8,            &e88        88  "));
        getServer().getConsoleSender().sendMessage(convertColors("     &6Y8a.    .a8P    Y8a.    .a8P  &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("       &6`Y8888Y'        `Y8888Y'    &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fOmnipotentialChests v    "+ getPluginVersion()));
        getServer().getConsoleSender().sendMessage(convertColors("         &fRunning on Spigot -  " + getMinecraftVersion(Bukkit.getServer())));
        getServer().getConsoleSender().sendMessage(convertColors("         &fMade by DevieTeam"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fAction: &2Plugin Enabled!"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
    }

    private void disablingMessage() {
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("      &6,ad8888ba,      ,ad8888ba,   &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("     &6d8'      `8b    d8'       `8b &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &6d8'        `8b  d8'            &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &688          88  88             &e88aaaaaaaa88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &688          88  88             &e88\"\"\"\"\"\"\"\"88"));
        getServer().getConsoleSender().sendMessage(convertColors("    &6Y8,        ,8P  Y8,            &e88        88  "));
        getServer().getConsoleSender().sendMessage(convertColors("     &6Y8a.    .a8P    Y8a.    .a8P  &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors("       &6`Y8888Y'        `Y8888Y'    &e88        88"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fOmnipotentialChests v    "+ getPluginVersion()));
        getServer().getConsoleSender().sendMessage(convertColors("         &fRunning on Spigot -  " + getMinecraftVersion(Bukkit.getServer())));
        getServer().getConsoleSender().sendMessage(convertColors("         &fMade by DevieTeam"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fAction: &cDisabling...."));
        getServer().getConsoleSender().sendMessage(convertColors(" "));

    }

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
        enablingMessage();
        instance = this;
        createClasses();
        initClasses();
    }

    @Override
    public void onDisable() {
        disablingMessage();
        if (instance == this)
            instance = null;
    }
}
