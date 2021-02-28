package me.simonxz.prison.files;

import me.simonxz.prison.Prison;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerManager implements Listener {

    Plugin plugin = Prison.getPlugin(Prison.class);

    @EventHandler
    public void createConfig(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        addPlayerConfig(p);
    }

    public void addPlayerConfig(Player p) {
        if (!Prison.cfgm.getPlayers().contains("Players." + p.getUniqueId().toString() + ".Name")) {
            Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Name", p.getName());
            Prison.cfgm.savePlayers();
        }
        if (!Prison.cfgm.getPlayers().contains("Players." + p.getUniqueId().toString() + ".Rank")) {
            Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Rank", Integer.valueOf(1));
            Prison.cfgm.savePlayers();
        }
        if (!Prison.cfgm.getPlayers().contains("Players." + p.getUniqueId().toString() + ".Prestige")) {
            Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Prestige", Integer.valueOf(0));
            Prison.cfgm.savePlayers();
        }
        if(!Prison.cfgm.getPlayers().contains("Players." + p.getUniqueId().toString() + ".Tokens")) {
            Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Tokens", Integer.valueOf(0));
            Prison.cfgm.savePlayers();
        }
    }
}
