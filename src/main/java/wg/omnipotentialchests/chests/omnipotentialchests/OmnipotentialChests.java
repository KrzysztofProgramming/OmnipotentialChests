package wg.omnipotentialchests.chests.omnipotentialchests;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import wg.omnipotentialchests.chests.omnipotentialchests.configs.ConfigsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.database.SQLManager;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.CommandsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.ListenersManager;

import java.util.logging.Logger;

import static wg.omnipotentialchests.chests.omnipotentialchests.ultimateguis.engine.ANSIColors.*;



public final class OmnipotentialChests extends JavaPlugin {

    @Getter
    private SQLManager sqlManager;
    public final Logger logger =  Logger.getLogger("");
    @Getter
    private static OmnipotentialChests instance;
    @Getter
    private CommandsManager commandsManager;
    @Getter
    private ListenersManager listenersManager;
    @Getter
    private ConfigsManager configsManager;

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
        logger.info(" ");
        logger.info(ANSI_YELLOW + "      ,ad8888ba,   " + ANSI_BRIGHT_YELLOW + "   ,ad8888ba,   88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "     d8'      `8b  " + ANSI_BRIGHT_YELLOW + "  d8'       `8b 88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    d8'        `8b " + ANSI_BRIGHT_YELLOW + " d8'            88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    88          88 " + ANSI_BRIGHT_YELLOW + " 88             88aaaaaaaa88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    88          88 " + ANSI_BRIGHT_YELLOW + " 88             88\"\"\"\"\"\"\"\"88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    Y8,        ,8P " + ANSI_BRIGHT_YELLOW + " Y8,            88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "     Y8a.    .a8P  " + ANSI_BRIGHT_YELLOW + "  Y8a.    .a8P  88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "       `Y8888Y'    " + ANSI_BRIGHT_YELLOW + "    `Y8888Y'    88        88" + ANSI_RESET);
        logger.info(" ");
        logger.info("         OmnipotentialChests v" + getPluginVersion());
        logger.info("         Running on Spigot - " + getMinecraftVersion(Bukkit.getServer()));
        logger.info("         Made by DevieTeam");
        logger.info(" ");
        logger.info("         Action: " + ANSI_GREEN + "Plugin Enabled!" + ANSI_RESET);
        logger.info(" ");
    }

    private void disablingMessage() {
        logger.info(" ");
        logger.info(ANSI_YELLOW + "      ,ad8888ba,   " + ANSI_BRIGHT_YELLOW + "   ,ad8888ba,   88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "     d8'      `8b  " + ANSI_BRIGHT_YELLOW + "  d8'       `8b 88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    d8'        `8b " + ANSI_BRIGHT_YELLOW + " d8'            88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    88          88 " + ANSI_BRIGHT_YELLOW + " 88             88aaaaaaaa88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    88          88 " + ANSI_BRIGHT_YELLOW + " 88             88\"\"\"\"\"\"\"\"88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "    Y8,        ,8P " + ANSI_BRIGHT_YELLOW + " Y8,            88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "     Y8a.    .a8P  " + ANSI_BRIGHT_YELLOW + "  Y8a.    .a8P  88        88" + ANSI_RESET);
        logger.info(ANSI_YELLOW + "       `Y8888Y'    " + ANSI_BRIGHT_YELLOW + "    `Y8888Y'    88        88" + ANSI_RESET);
        logger.info(" ");
        logger.info("         OmnipotentialChests v" + getPluginVersion());
        logger.info("         Running on Spigot - " + getMinecraftVersion(Bukkit.getServer()));
        logger.info("         Made by DevieTeam");
        logger.info(" ");
        logger.info("         Action: " + ANSI_RED + "Disabling..." + ANSI_RESET);
        logger.info(" ");

    }

    public void createClasses() {
        this.sqlManager = new SQLManager();
        this.configsManager = new ConfigsManager();
        this.listenersManager = new ListenersManager();
        this.commandsManager = new CommandsManager();
    }

    public void initClasses() {
        this.sqlManager.init();
        this.configsManager.init();
        this.listenersManager.init();
        this.commandsManager.init();
    }

    @Override
    public void onEnable() {
        instance = this;
        createClasses();
        initClasses();
        enablingMessage();
    }

    @Override
    public void onDisable() {
        this.getListenersManager().disable();
        if (instance == this)
            instance = null;
        disablingMessage();
    }
}
