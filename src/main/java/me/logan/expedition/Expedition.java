package me.logan.expedition;

import me.logan.expedition.commands.ExChest;
import me.logan.expedition.commands.ExLootCommand;
import me.logan.expedition.data.LootTableDataManager;
import me.logan.expedition.enums.Monsters;
import me.logan.expedition.listeners.ChestPlaceListener;
import me.logan.expedition.listeners.LootViewListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Expedition extends JavaPlugin {

    private static LootTableDataManager lootTableManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Monsters.setValuesFromConfig(getConfig());
        getCommand("ExChest").setExecutor(new ExChest(this));
        getCommand("Ex").setExecutor(new ExLootCommand(this));
        getCommand("Extier").setExecutor(new ExLootCommand(this));
        registerEvents();
        registerLoottable();
    }

    @Override
    public void onDisable() {

    }



    private void registerEvents() {
        ChestPlaceListener chestPlaceListener = new ChestPlaceListener(this);
        LootViewListener lootViewListener = new LootViewListener(this);




        getServer().getPluginManager().registerEvents(chestPlaceListener, this);
        getServer().getPluginManager().registerEvents(lootViewListener, this);

    }


    public void registerLoottable() {
        lootTableManager = new LootTableDataManager(this, this.getDataFolder().getPath());
    }


}
