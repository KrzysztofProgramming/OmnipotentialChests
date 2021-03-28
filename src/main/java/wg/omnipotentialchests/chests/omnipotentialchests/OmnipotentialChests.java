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
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("      &6,ad8888ba,      &e,ad8888ba,   88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("     &6d8'      `8b    &ed8'       `8b 88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &6d8'        `8b  &ed8'            88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &688          88  &e88             88aaaaaaaa88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &688          88  &e88             88\"\"\"\"\"\"\"\"88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &6Y8,        ,8P  &eY8,            88        88  "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("     &6Y8a.    .a8P    &eY8a.    .a8P  88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("       &6`Y8888Y'        &e`Y8888Y'    88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fOmnipotentialChests v"+ getPluginVersion()));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fRunning on Spigot - " + getMinecraftVersion(Bukkit.getServer())));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fMade by DevieTeam"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fAction: &2Plugin Enabled!"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));
    }

    private void disablingMessage() {
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("      &6,ad8888ba,      &e,ad8888ba,   88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("     &6d8'      `8b    &ed8'       `8b 88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &6d8'        `8b  &ed8'            88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &688          88  &e88             88aaaaaaaa88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &688          88  &e88             88\"\"\"\"\"\"\"\"88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("    &6Y8,        ,8P  &eY8,            88        88  "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("     &6Y8a.    .a8P    &eY8a.    .a8P  88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("       &6`Y8888Y'        &e`Y8888Y'    88        88"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fOmnipotentialChests v"+ getPluginVersion()));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fRunning on Spigot - " + getMinecraftVersion(Bukkit.getServer())));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fMade by DevieTeam"));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors("         &fAction: &cDisabling...."));
        Bukkit.getServer().getConsoleSender().sendMessage(convertColors(" "));

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
