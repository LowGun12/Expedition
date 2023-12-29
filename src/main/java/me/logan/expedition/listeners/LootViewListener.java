package me.logan.expedition.listeners;

import me.logan.expedition.Expedition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootViewListener implements Listener {

    private final Expedition main;

    public LootViewListener(Expedition main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Bukkit.getLogger().info("clicked");
        Inventory clickedInv = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedInv != null && clickedItem != null && e.getView().getTitle() == "Expedition Loot") {
            Bukkit.getLogger().info("good inv");
            if (clickedItem.getType() == Material.CHEST) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                String[] words = displayName.split(" ");
                Bukkit.getLogger().info("Good checks");
                try {
                    if (words.length == 2) {
                        int tier = Integer.parseInt(words[1]);
                        Inventory lootInv = Bukkit.createInventory(null, 54, "Tier " + tier);
                        Bukkit.getLogger().info("Passed inv");

                        player.openInventory(lootInv);
                        Bukkit.getLogger().info("Opened inv");
                    }
                } catch (NumberFormatException ex) {
                        Bukkit.getLogger().info("exception");
                    }
                }
            }
        }





}
