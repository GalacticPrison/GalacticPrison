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
                Donor(p);

            }

        }
        return true;
    }

    @EventHandler
    private void inventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle() != "Donor Prefix") return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§7Member§8>")); setPrefix(p, "member"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8<§7Test§8>")); setPrefix(p, "donor1"); p.closeInventory();
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lReset Prefix")); resetPrefix(p); p.closeInventory();

    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().getType().getDefaultTitle() == "Donor Prefix") {
            e.setCancelled(true);
        }
    }
    
    public static void Donor(Player p) {

        //creating the inventory
        Inventory inv = Bukkit.getServer().createInventory(null, 9, "Donor Prefix");

        // Reset prefix
        inv.setItem(0, createGuiItem(Material.WHITE_DYE, "§f§lReset Prefix", "§7Reverse your prefix back to your", "§7default group prefix"));

        // Making the items for the donor prefixes
        if(p.hasPermission("prison.prefixmember")) {
            inv.setItem(1, createGuiItem(Material.GRAY_DYE, "§8<§7Member§8>", "§7Click to apply this prefix"));
        }
        if(p.hasPermission("prison.prefixdonor1")) {
            inv.setItem(2, createGuiItem(Material.GRAY_DYE, "§8<§7Test§8>", "§7Click to apply this prefix"));
        }

        p.openInventory(inv);
        
    }

    public void setPrefix(Player p, String prefix) {

        String new_prefix = null;
        if(prefix=="member") new_prefix = "\"&8<&7Member&8> &f\"";
        else if(prefix=="donor1") new_prefix = "\"&8<&7IdkTest&8> &f\"";

        if(new_prefix != null) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " +p.getName()+" meta setprefix "+new_prefix);
            p.sendMessage(ChatColor.GREEN + "New prefix has been applied!");
        } else {
            p.sendMessage(ChatColor.RED + "Something went wrong applying your new prefix!");
        }
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