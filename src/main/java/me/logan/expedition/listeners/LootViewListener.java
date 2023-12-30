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
    private final ExLootCommand exlootCommand;

    public LootViewListener(Expedition main, ExLootCommand exLootCommand) {
        this.main = main;
        this.exlootCommand = exLootCommand;
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
                        exlootCommand.setEditingTier(player, tier); // Store the tier player is currently editing

                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            } else if (exlootCommand.isEditingTier(player)) {
                int editingTier = exlootCommand.getEditingTier(player);
                String fileName = editingTier + ".json";
                File lootFile = new File(main.getDataFolder(), "Loot Tables/" + fileName);

                if (!lootFile.exists()) {
                    try {
                        lootFile.createNewFile();
                    } catch (IOException ex) {
                        main.getLogger().log(Level.SEVERE, "Could not create loot file: " + fileName, ex);
                        return;
                    }
                }

                try (FileWriter writer = new FileWriter(lootFile, true)) {
                    writer.write(clickedItem.getType().name() + "\n"); // Write item name/type to the file
                } catch (IOException ex) {
                    main.getLogger().log(Level.SEVERE, "Could not write to loot file: " + fileName, ex);
                }
            }
        }
    }
}