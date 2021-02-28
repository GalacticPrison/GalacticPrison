package me.simonxz.prison.leaderboard;

import me.simonxz.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

public class TokenTop implements CommandExecutor, Listener {

    Plugin plugin = Prison.getPlugin(Prison.class);

    public static void main2(Player p) {
        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Integer tokens = Prison.cfgm.getPlayers().getInt("Players." + onlinePlayer.getUniqueId().toString() + ".Tokens");
            if (Prison.cfgm.getPlayers().contains("Players." + onlinePlayer.getUniqueId().toString() + ".Tokens"))
                map.put(tokens, onlinePlayer.getName());
        }
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer != null && offlinePlayer.getName() != null) {
                Integer tokens = Prison.cfgm.getPlayers().getInt("Players." + offlinePlayer.getUniqueId().toString() + ".Tokens");
                if (Prison.cfgm.getPlayers().contains("Players." + offlinePlayer.getUniqueId().toString() + ".Tokens"))
                    map.put(tokens, offlinePlayer.getName());
            }
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lTOP 5 TOKENS"));
        int n = 1;

        NavigableMap<Integer, String> nmap = map.descendingMap();
        for (NavigableMap.Entry<Integer, String> entry : nmap.entrySet()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e#"+n+". &f" + entry.getValue() + " &8-&b Tokens " + entry.getKey()));
            n++;
            if (n == 6) {
                break;
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p  = (Player) sender;
            if (args.length == 0) {
                main2(p);
            }
        }
        return true;
    }
}
