package me.logan.expedition.listeners;

import me.logan.expedition.Expedition;
import me.logan.expedition.commands.ExLootCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class LootViewListener implements Listener {

    private final Expedition main;

    public LootViewListener(Expedition main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory clickedInv = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedInv != null && clickedItem != null && e.getView().getTitle().equals("Expedition Loot")) {
            e.setCancelled(true); // Prevents players from taking items from this inventory
            if (clickedItem.getType() == Material.CHEST) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                String[] words = displayName.split(" ");
                try {
                    if (words.length == 2) {
                        int tier = Integer.parseInt(words[1]);
                        Inventory lootInv = Bukkit.createInventory(null, 54, "Tier " + tier);

                        // Open the specific tier inventory for the player
                        player.openInventory(lootInv);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory clickedInv = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedInv != null && clickedItem != null && clickedInv == player.getInventory()) {
            // Check if the player's inventory item click logic matches your conditions
            // For instance, add to lootInv if needed
            // Example condition:
            if (e.getClickedInventory().equals(player.getInventory())) {
                Inventory topInv = player.getOpenInventory().getTopInventory();
                if (topInv != null && e.getView().getTitle().startsWith("Tier ")) {
                    e.setCancelled(true);
                    topInv.addItem(clickedItem.clone()); // Add a copy of the clicked item to the loot inventory
                }
            }
        }
    }
}
