package me.simonxz.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class DonorPrefix implements CommandExecutor, Listener {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (sender.hasPermission("prison.donorprefix")) {

                //opening the gui
                prefixEditor(p);

            }

        }
        return true;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getView().getTitle() != "Prefix Editor") return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§7Member§8>")) setPrefix(p, "member"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§3Subscriber§8>")) setPrefix(p, "subscriber"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§9Penguin§8>")) setPrefix(p, "penguin"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§9Penguin§b+§8>")) setPrefix(p, "penguin+"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§9Penguin§b++§8>")) setPrefix(p, "penguin++"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§9Penguin§b+++§8>")) setPrefix(p, "penguin+++"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lReset Prefix")) resetPrefix(p); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lExit")) p.closeInventory();
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().getType().getDefaultTitle() == "Prefix Editor") {
            e.setCancelled(true);
        }
    }

    public static void prefixEditor(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "Prefix Editor");

        // Reset prefix
        inv.setItem(0, createGuiItem(Material.WHITE_DYE, "§f§lReset Prefix", "§7Reverse your prefix back to your", "§7default group prefix"));

        // Figure out which members can apply which prefixes
        if(p.hasPermission("prefixeditor.Member")) {
            inv.setItem(1, createGuiItem(Material.GRAY_DYE, "§8<§7Member§8>", "§7Click to apply this prefix"));
        }
        if(p.hasPermission("prefixeditor.Subscriber")) {
            inv.setItem(2, createGuiItem(Material.CYAN_DYE, "§8<§3Subscriber§8>", "§7Click to apply this prefix"));
        }
        if(p.hasPermission("prefixeditor.Penguin")) {
            inv.setItem(3, createGuiItem(Material.LIGHT_BLUE_DYE, "§8<§9Penguin§8>", "§7Click to apply this prefix"));
        }
        if(p.hasPermission("prefixeditor.Penguin+")) {
            inv.setItem(4, createGuiItem(Material.LIGHT_BLUE_DYE, "§8<§9Penguin§b+§8>", "§7Click to apply this prefix"));
        }
        if(p.hasPermission("prefixeditor.Penguin++")) {
            inv.setItem(5, createGuiItem(Material.LIGHT_BLUE_DYE, "§8<§9Penguin§b++§8>", "§7Click to apply this prefix"));
        }
        if(p.hasPermission("prefixeditor.Penguin+++")) {
            inv.setItem(6, createGuiItem(Material.LIGHT_BLUE_DYE, "§8<§9Penguin§b+++§8>", "§7Click to apply this prefix"));
        }
        inv.setItem(7, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "§7"));
        // Exit slot
        inv.setItem(8, createGuiItem(Material.RED_DYE, "§c§lExit", "§cExit and close GUI"));

        p.openInventory(inv);
    }

    public void setPrefix(Player p, String prefix) {

        String new_prefix = null;
        if(prefix=="member") new_prefix = "\"&8<&7Member&8> &f\"";
        else if(prefix=="subscriber") new_prefix = "\"&8<&3Subscriber&8> &3\"";
        else if(prefix=="penguin") new_prefix = "\"&8<&9Penguin&8> &9\"";
        else if(prefix=="penguin+") new_prefix = "\"&8<&9Penguin&b+&8> &9\"";
        else if(prefix=="penguin++") new_prefix = "\"&8<&9Penguin&b++&8> &9\"";
        else if(prefix=="penguin+++") new_prefix = "\"&8<&9Penguin&b+++&8> &9\"";

        if(new_prefix != null) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user "+p.getName()+" meta setprefix "+new_prefix);
            p.sendMessage("§aNew prefix has been applied!");
        } else
            p.sendMessage("§cSomething went wrong applying your new prefix!");

    }

    public void resetPrefix(Player p) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user "+p.getName()+" meta clear");
    }

    protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}