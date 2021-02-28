package me.simonxz.prison.API;

import me.simonxz.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Api {

    public static int getRank(Player p) {
        return getRank(Bukkit.getOfflinePlayer(p.getUniqueId()));
    }
    public static int getRank(OfflinePlayer p) {
        try {
            return Prison.cfgm.getPlayers().getInt("Players." + p.getUniqueId().toString() + ".Rank");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getTokens(Player p) { return getTokens(Bukkit.getOfflinePlayer(p.getUniqueId())); }
    public static int getTokens(OfflinePlayer p) {
        try {
            return Prison.cfgm.getPlayers().getInt("Players." + p.getUniqueId().toString() + ".Tokens");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
