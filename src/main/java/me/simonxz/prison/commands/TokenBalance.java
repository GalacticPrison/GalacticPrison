package me.simonxz.prison.commands;

import me.simonxz.prison.API.Api;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TokenBalance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lTOKENS: " + Api.getTokens(p)));
        }
        return true;
    }
}
