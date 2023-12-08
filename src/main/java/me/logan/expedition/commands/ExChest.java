package me.logan.expedition.commands;

import me.logan.expedition.Expedition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Collections;

public class ExChest implements CommandExecutor {

    private final Expedition main;

    public ExChest(Expedition main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (sender.hasPermission("Expedition.admin")) {
            if (args.length == 1) {
                int tier = Integer.parseInt(args[0]);
                ItemStack tieredChest = createTieredChest(Material.CHEST, tier);
                player.getInventory().addItem(tieredChest);
                player.sendMessage(ChatColor.GREEN + "You received a Tier " + tier + " expedition chest!");
            }
        }

        return false;
    }


    public ItemStack createTieredChest(Material material, Integer tier) {
        ItemStack is = new ItemStack(material);
        ItemMeta isMeta = is.getItemMeta();
        isMeta.getPersistentDataContainer().set(new NamespacedKey(main, "tier"), PersistentDataType.INTEGER, tier);
        isMeta.setDisplayName(ChatColor.RED + "Tier " + tier + " expedition chest");
        isMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Place in your tier " + tier + " zone"));
        is.setItemMeta(isMeta);
        return is;
    }
}