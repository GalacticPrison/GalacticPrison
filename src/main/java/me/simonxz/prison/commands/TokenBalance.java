package me.simonxz.prison.commands;

import me.simonxz.prison.API.Api;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TokenBalance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            double tokens = Api.getTokens(p);
            double amount = Double.parseDouble(String.valueOf(tokens));
            DecimalFormat formatter = new DecimalFormat("#,###");

            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lTOKENS: &7" + formatter.format(amount)));
        }
        return true;
    }
}
