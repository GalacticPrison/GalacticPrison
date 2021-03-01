package me.simonxz.prison;

import me.simonxz.prison.commands.DonorPrefix;
import me.simonxz.prison.commands.Help;
import me.simonxz.prison.commands.Rankup.Prestige;
import me.simonxz.prison.commands.Rankup.Rankup;
import me.simonxz.prison.commands.TokenBalance;
import me.simonxz.prison.commands.admincommands.TokenGive;
import me.simonxz.prison.files.ConfigManager;
import me.simonxz.prison.files.PlayerManager;
import me.simonxz.prison.leaderboard.PrestigeTop;
import me.simonxz.prison.leaderboard.TokenTop;
import me.simonxz.prison.placeholders.Placeholders;
import me.simonxz.prison.tokens.TokenManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class Prison extends JavaPlugin implements Listener {

    private static final Logger log = Logger.getLogger("Minecraft");

    public static ConfigManager cfgm;

    private static Economy econ = null;
    private static Permission perms = null;


    @Override
    public void onEnable() {

        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName()}));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }



        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            throw new RuntimeException("Could not find PlaceholderAPI!! Plugin can not work without it!");
        }



        loadConfigManager();
        getConfig().options().copyDefaults();
        config();
        // Plugin startup logic


        //COMMANDS
        this.getCommand("help").setExecutor(new Help());
        this.getCommand("prefix").setExecutor(new DonorPrefix());
        this.getCommand("rankup").setExecutor(new Rankup());
        this.getCommand("prestige").setExecutor(new Prestige());
        this.getCommand("prestigetop").setExecutor(new PrestigeTop());
        this.getCommand("tokentop").setExecutor(new TokenTop());
        this.getCommand("tokens").setExecutor(new TokenBalance());
        this.getCommand("givetokens").setExecutor(new TokenGive());
        //Events
        getServer().getPluginManager().registerEvents(new DonorPrefix(), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveMessage(), this);
        getServer().getPluginManager().registerEvents(new PlayerManager(), this);
        getServer().getPluginManager().registerEvents(new Rankup(), this);
        getServer().getPluginManager().registerEvents(new PrestigeTop(), this);
        getServer().getPluginManager().registerEvents(new TokenTop(), this);
        getServer().getPluginManager().registerEvents(new TokenManager(), this);

        new Placeholders().register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadConfigManager() {
        cfgm = new ConfigManager();
        cfgm.setupPlayers();
    }

    public void config() {
        try {
            if(!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.uml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = rsp.getProvider();
        return (econ != null);
    }

    public static Economy getEconomy(){
        return econ;
    }
}
