package me.logan.expedition.commands;

import me.logan.expedition.Expedition;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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

        Inventory inventory = Bukkit.createInventory(null, 53, "Expedition Loot");


        return false;
    }
}
