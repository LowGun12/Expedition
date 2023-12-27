package me.logan.expedition;

import me.logan.expedition.commands.ExChest;
import me.logan.expedition.commands.ExLootCommand;
import me.logan.expedition.listeners.ChestPlaceListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Expedition extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("ExChest").setExecutor(new ExChest(this));
        getCommand("Ex loot").setExecutor(new ExLootCommand(this));
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    private void registerEvents() {
        ChestPlaceListener chestPlaceListener = new ChestPlaceListener(this);




        getServer().getPluginManager().registerEvents(chestPlaceListener, this);

    }

}
