package me.logan.expedition.utils;

import me.logan.expedition.Expedition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
    public static void addItemToPlayer(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(new ItemStack[]{item});
        }
    }
}
