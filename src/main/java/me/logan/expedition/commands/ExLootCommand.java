package me.logan.expedition.commands;

import me.logan.expedition.Expedition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExLootCommand implements CommandExecutor {
    private final Expedition main;

    public ExLootCommand(Expedition main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("loot")) {
            Player player = (Player) sender;

            Inventory tierGui = Bukkit.createInventory(null, 9, "Expedition Loot");

            File lootFolder = new File(main.getDataFolder(), "Loot Tables");
            if (!lootFolder.exists() || !lootFolder.isDirectory()) {
                return false;


            }
            for (int i = 1; i <= 6; i++) {
                File lootFile = new File(lootFolder, i + ".json");
                if (lootFile.exists()) {
                    try {
                        FileReader reader = new FileReader(lootFile);
                        String itemName = ChatColor.RED + "Tier" + i;
                        ItemStack item = new ItemStack(Material.CHEST);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(itemName);
                        item.setItemMeta(meta);
                        tierGui.setItem(i - 1, item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            player.openInventory(tierGui);
        }
        return true;
    }
}
