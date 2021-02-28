package me.simonxz.prison;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import me.simonxz.prison.API.Api;
import me.simonxz.prison.commands.DonorPrefix;
import me.simonxz.prison.commands.Help;
import me.simonxz.prison.commands.Rankup.Prestige;
import me.simonxz.prison.commands.Rankup.Rankup;
import me.simonxz.prison.commands.TokenBalance;
import me.simonxz.prison.files.ConfigManager;
import me.simonxz.prison.files.PlayerManager;
import me.simonxz.prison.leaderboard.PrestigeTop;
import me.simonxz.prison.leaderboard.TokenTop;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Logger;

public final class Prison extends JavaPlugin implements Listener {

    private static final Logger log = Logger.getLogger("Minecraft");

    public static ConfigManager cfgm;

    private static Economy econ = null;
    private static Permission perms = null;


    //
    //this is for a test
    //

    //




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

        registerPlaceholders();


        loadConfigManager();
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

        //Events
        getServer().getPluginManager().registerEvents(new DonorPrefix(), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveMessage(), this);
        getServer().getPluginManager().registerEvents(new PlayerManager(), this);
        getServer().getPluginManager().registerEvents(new Rankup(), this);
        getServer().getPluginManager().registerEvents(new PrestigeTop(), this);
        getServer().getPluginManager().registerEvents(new TokenTop(), this);

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
                getLogger().info("Config.yml found, loading!");
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

    public void registerPlaceholders() {
        PlaceholderAPI.registerPlaceholderHook("prison", new PlaceholderHook() {
            @Override
            public String onRequest(OfflinePlayer p, String params) {
                if(p != null && p.isOnline()) {
                    return onPlaceholderRequest(p.getPlayer(), params);
                }
                return null;
            }
            @Override
            public String onPlaceholderRequest(Player p, String params) {
                if (p == null) {
                    return null;
                }
                if (params.equalsIgnoreCase("prestige")) {
                    String prestige = cfgm.getPlayers().getString("Players." + p.getUniqueId().toString() + ".Prestige");
                    return prestige;
                }
                if (params.equalsIgnoreCase("rank")) {
                    int rank = Api.getRank(p);
                    String a = Rankup.rankName(p, rank);
                    return a;
                }
                if (params.equalsIgnoreCase("rankbar")) {
                    int rank = Api.getRank(p);
                    int prestige = Integer.parseInt(cfgm.getPlayers().getString("Players." + p.getUniqueId().toString() + ".Prestige") + 1);
                    double p_bal = econ.getBalance(p);
                    long rankcost = 0;
                    if (prestige < 1) {
                        rankcost = Rankup.rankCost(p, rank);
                    } else {
                        rankcost = Rankup.rankCost(p, rank) * prestige;
                    }
                    double percentage = (p_bal / rankcost) * 100;
                    if (percentage >= 100) {
                        percentage = 100.00;
                    }
                    DecimalFormat round = new DecimalFormat("#.##");
                    round.setRoundingMode(RoundingMode.HALF_DOWN);

                    String bar = "";
                    if(percentage >= 100) { bar = "&a||||||||||"; }
                    else if(percentage >= 90 && percentage < 100) { bar = "&a|||||||||&7|"; }
                    else if(percentage >= 80 && percentage < 90) { bar = "&a||||||||&7||"; }
                    else if(percentage >= 70 && percentage < 80) { bar = "&a|||||||&7|||"; }
                    else if(percentage >= 60 && percentage < 70) { bar = "&a|||||||&7|||"; }
                    else if(percentage >= 50 && percentage < 60) { bar = "&a||||||&7||||"; }
                    else if(percentage >= 40 && percentage < 50) { bar = "&a|||||&7|||||"; }
                    else if(percentage >= 30 && percentage < 40) { bar = "&a||||&7||||||"; }
                    else if(percentage >= 20 && percentage < 30) { bar = "&a|||&7|||||||"; }
                    else if(percentage >= 10 && percentage < 20) { bar = "&a||&7||||||||"; }
                    else if(percentage >= 0 && percentage < 10) { bar = "&a|&7|||||||||"; }

                    return bar + " " + round.format(percentage) + "%";

                }

                return null;
            }

        });
    }
}
