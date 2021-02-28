package me.simonxz.prison.tokens;

import me.simonxz.prison.Prison;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TokenManager implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        int tokens = Prison.cfgm.getPlayers().getInt("Players." + p.getUniqueId().toString() + ".Tokens");
        long new_tokens = (tokens + 1);
        Prison.cfgm.getPlayers().set("Players." + p.getUniqueId().toString() + ".Tokens", Long.valueOf(new_tokens));
        Prison.cfgm.savePlayers();
    }


}

