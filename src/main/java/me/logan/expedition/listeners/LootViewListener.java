package me.logan.expedition.listeners;

import me.logan.expedition.Expedition;
import me.logan.expedition.commands.ExLootCommand;
import me.logan.expedition.utils.ItemStackSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Bukkit;

public class LootViewListener implements Listener {

    private final Expedition main;

    private ItemStackSerializer itemStackSerializer;

    public LootViewListener(Expedition main) {
        this.main = main;
        this.itemStackSerializer = new ItemStackSerializer(main.getDataFolder().getPath());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory clickedInv = e.getClickedInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedInv != null && clickedItem != null && e.getView().getTitle().equals("Expedition Loot")) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.CHEST) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                String[] words = displayName.split(" ");
                try {
                    if (words.length == 2) {
                        int tier = Integer.parseInt(words[1]);
                        itemStackSerializer.loadLootGui(tier, player);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (e.getView().getTitle().startsWith("Tier ")) {
            e.setCancelled(true);
            if (e.getWhoClicked() != null && e.getClickedInventory().equals(e.getClickedInventory())) {
                e.setCancelled(true);

            }
        }

        if (clickedInv != null && clickedItem != null && e.getView().getTitle().equals("Edit Tier Loot")) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.CHEST) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                String[] words = displayName.split(" ");
                try {
                    if (words.length == 2) {
                        int tier = Integer.parseInt(words[1]);
                        Inventory lootInv = Bukkit.createInventory(null, 54, "Edit Tier " + tier);
                        itemStackSerializer.loadLootGui(tier, player);
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
            if (e.getClickedInventory().equals(player.getInventory())) {
                Inventory lootInv = player.getOpenInventory().getTopInventory();
                if (lootInv != null && e.getView().getTitle().startsWith("Edit ")) {
                    String itemName = e.getView().getTitle();
                    String[] words = itemName.split(" ");
                    int tier = Integer.parseInt(words[2]);
                   itemStackSerializer.saveItemToJson(tier, clickedItem);
                    e.setCancelled(true);
                    lootInv.addItem(clickedItem.clone());
                }
            }
        }
    }
}
