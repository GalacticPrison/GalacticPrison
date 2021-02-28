package me.simonxz.prison.commands.admincommands;

import me.simonxz.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.tools.jar.Main;

public class TokenGive implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        long tokens = Prison.cfgm.getPlayers().getLong("Players." + p.getUniqueId().toString() + ".Tokens");

        if (!sender.hasPermission("prison.givetokens")) {
            sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
            return true;
        }
        if (args.length == 2) {
            if(Bukkit.getServer().getPlayer(args[0])!=null) {
                Player rec = Bukkit.getServer().getPlayer(args[0]);
                long amount = Long.parseLong(args[1]);
                if (amount > 0 && amount <= 1000000000) {
                    giveCrystals(rec, amount);
                    rec.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e"));
                    rec.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have been given " + amount + " tokens."));
                }
                else p.sendMessage(ChatColor.RED + "Amount must be between 1 & 1,000,000,000");
                return true;
            }
            else p.sendMessage(ChatColor.RED + "Unknown player");
            return true;
        } else p.sendMessage(ChatColor.RED + "Missing arguments /givetokens <player> <amount>");
        return true;
    }


    public void giveCrystals(Player p, long amount) {
        long balance = Prison.cfgm.getPlayers().getLong("Players." + p.getUniqueId().toString() + ".Tokens");
        long new_balance = (balance + amount);
        long max_balance = 1000000000;
        if (new_balance >= max_balance) new_balance = max_balance;
        if(new_balance <= 0) new_balance = 0;
        Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Tokens", new_balance);
        Prison.cfgm.savePlayers();
    }

    public void takeCrystals(Player p, long amount) {
        long balance = Prison.cfgm.getPlayers().getLong("Players." + p.getUniqueId().toString() + ".Tokens");
        long new_balance = (balance - amount);
        long max_balance = 1000000000;
        if(new_balance >= max_balance) new_balance = max_balance;
        if(new_balance <= 0) new_balance = 0;
        Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Crystals", new_balance);
        Prison.cfgm.savePlayers();
    }
}
