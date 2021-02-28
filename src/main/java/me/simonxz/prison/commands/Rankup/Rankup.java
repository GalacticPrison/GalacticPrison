package me.simonxz.prison.commands.Rankup;

import me.simonxz.prison.API.Api;
import me.simonxz.prison.Prison;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;

public class Rankup implements CommandExecutor, Listener {

    Plugin plugin = Prison.getPlugin(Prison.class);

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

        if (cmd.getName().equals("rankup") && sender instanceof Player) {

            Player p = (Player) sender;

            if(args.length == 0) {
                doRankup(p);
            } else {
                if(args[0].equalsIgnoreCase("max")) {
                    int rank = Api.getRank(p);
                    int ranksleft = 10 - rank;
                    if(ranksleft > 0) {
                        new BukkitRunnable() {
                            int loop = ranksleft;
                            @Override
                            public void run() {
                                doRankup(p);
                                loop--;
                                if(loop==0) this.cancel();
                            }
                        }.runTaskTimer(plugin, 0L, 2L).getTaskId();
                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou're top rank."));
                    }
                }
                else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lIncorrect arguments: &e/rankup <max>"));
                }
            }
        }
        return true;
    }

    public void doRankup(Player p) {

        int rank = Prison.cfgm.getPlayers().getInt("Players." + p.getUniqueId().toString() + ".Rank");
        long cost = rankCost(p, rank);
        int prestige = Prison.cfgm.getPlayers().getInt("Players." + p.getUniqueId().toString() + ".Prestige");
        int new_rank = rank + 1;

        long multiplier = prestige + 1;
        long charge = 0;
        if (multiplier >= 1) {
            charge = cost * multiplier;
        } else {
            charge = cost;
        }

        if(rank >= 1 && rank < 10) {
            EconomyResponse r = Prison.getEconomy().withdrawPlayer(p, charge);
            if(r.transactionSuccess()) {

                Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Rank", new_rank);
                Prison.cfgm.savePlayers();

                //Messages
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have ranked up to &f&l" + rankName(p, new_rank) +
                        " &7( &c- &l$&c"+ NumberFormat.getNumberInstance().format(charge)+" &7)"));

                Location loc = p.getLocation();
                p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 40, .5, .5, .5, 0);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            }else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError&8: &7You require &c$" + NumberFormat.getNumberInstance().format(charge) +
                        "&7 to rankup to &f&l" + rankName(p, new_rank) + "&7!"));
            }
        }else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError&8: &7You're at top rank. How about you try &c&l/prestige&7?"));
        }
    }

    public static String rankName(Player p, int rank) {
        String a = null;
        //Select next rank
        if(rank == 1) {
            a = "A";
        } else if(rank == 2) {
            a = "B";
        } else if(rank == 3) {
            a = "C";
        } else if(rank == 4) {
            a = "D";
        } else if(rank == 5) {
            a = "E";
        } else if(rank == 6) {
            a = "F";
        } else if(rank == 7) {
            a = "G";
        } else if(rank == 8) {
            a = "H";
        } else if(rank == 9) {
            a = "I";
        } else if(rank == 10) {
            a = "J";
        } else if(rank == 11) {
            a = "K";
        } else if(rank == 12) {
            a = "L";
        } else if(rank == 13) {
            a = "M";
        } else if(rank == 14) {
            a = "N";
        } else if(rank == 15) {
            a = "O";
        } else if(rank == 16) {
            a = "P";
        } else if(rank == 17) {
            a = "Q";
        } else if(rank == 18) {
            a = "R";
        } else if(rank == 19) {
            a = "S";
        } else if(rank == 20) {
            a = "T";
        } else if(rank == 21) {
            a = "U";
        } else if(rank == 22) {
            a = "V";
        } else if(rank == 23) {
            a = "W";
        } else if(rank == 23) {
            a = "X";
        } else if(rank == 24) {
            a = "Y";
        } else if(rank == 25) {
            a = "Z";
        }
        return a;
    }

    public static long rankCost(Player p, int rank) {
        long a = 0;
        if(rank == 1) {
            a = 50000;
        } else if(rank == 2) {
            a = 100000;
        } else if(rank == 3) {
            a = 250000;
        } else if(rank == 4) {
            a = 500000;
        } else if(rank == 5) {
            a = 2000000;
        } else if(rank == 6) {
            a = 10000000;
        } else if(rank == 7) {
            a = 25000000;
        } else if(rank == 8) {
            a = 80000000;
        } else if(rank == 9) {
            a = 300000000;
        } else if(rank == 10) {
            a = 800000000;
        } else if(rank == 11) {
            a = 0;
        } else if(rank == 12) {
            a = 0;
        } else if(rank == 13) {
            a = 0;
        } else if(rank == 14) {
            a = 0;
        } else if(rank == 15) {
            a = 0;
        } else if(rank == 16) {
            a = 0;
        } else if(rank == 17) {
            a = 0;
        } else if(rank == 18) {
            a = 0;
        } else if(rank == 19) {
            a = 0;
        } else if(rank == 20) {
            a = 0;
        } else if(rank == 21) {
            a = 0;
        } else if(rank == 22) {
            a = 0;
        } else if(rank == 23) {
            a = 0;
        } else if(rank == 24) {
            a = 0;
        } else if(rank == 25) {
            a = 0;
        }
        return a;
    }
}
