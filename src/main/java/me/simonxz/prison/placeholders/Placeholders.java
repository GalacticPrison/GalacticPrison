package me.simonxz.prison.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.simonxz.prison.API.Api;
import me.simonxz.prison.Prison;
import me.simonxz.prison.Utils;
import me.simonxz.prison.commands.Rankup.Rankup;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.math.RoundingMode;
import java.text.DecimalFormat;


public class Placeholders extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Simon_Xz";
    }

    @Override
    public String getIdentifier() {
        return "galacticprison";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, String identifier) {

        if(identifier.equalsIgnoreCase("rank")) {
            int rank = Api.getRank(p);
            String a = Rankup.rankName((Player) p, rank);
            return a;
        }

        if(identifier.equalsIgnoreCase("prestige")) {
            String prestige = Prison.cfgm.getPlayers().getString("Players." + p.getUniqueId().toString() + ".Prestige");
            return prestige;
        }

        if(identifier.equalsIgnoreCase("rankbar")) {
            int rank = Api.getRank(p);
            int prestige = Integer.parseInt(Prison.cfgm.getPlayers().getString("Players." + p.getUniqueId().toString() + ".Prestige") + 1);
            double p_bal = Prison.getEconomy().getBalance(p);
            long rankcost = 0;
            if (prestige < 1) {
                rankcost = Rankup.rankCost((Player) p, rank);
            } else {
                rankcost = Rankup.rankCost((Player) p, rank) * prestige;
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

        if(identifier.equalsIgnoreCase("tokensformatted")) {
            int tokens = Api.getTokens(p);
            return Utils.format(tokens);
        }
        return null;
    }
}
