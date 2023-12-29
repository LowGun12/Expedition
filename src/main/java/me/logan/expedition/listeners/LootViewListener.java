package me.logan.expedition.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootViewListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory clickedInv = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedInv != null && clickedItem != null && e.getView().getTitle() == "Expedition Loot") {

            if (clickedItem.getType() == Material.CHEST) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                String[] words = displayName.split(" ");
                try {
                    if (words.length == 2) {
                        int tier = Integer.parseInt(words[1]);
                        Inventory lootInv = Bukkit.createInventory(null, 54, "Tier" + tier);

                        player.openInventory(lootInv);
                    }
                } catch (NumberFormatException ex) {

                    }
                }
            }
        }





}
