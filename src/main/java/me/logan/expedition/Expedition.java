package me.logan.expedition;

import me.logan.expedition.commands.ExChest;
import me.logan.expedition.commands.ExLootCommand;
import me.logan.expedition.data.LootTableDataManager;
import me.logan.expedition.database.DatabaseManager;
import me.logan.expedition.enums.Monsters;
import me.logan.expedition.listeners.ChestPlaceListener;
import me.logan.expedition.listeners.LootViewListener;
import me.logan.expedition.utils.ItemStackSerializer;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Expedition extends JavaPlugin {

    private static LootTableDataManager lootTableManager;
    private ItemStackSerializer itemStackSerializer;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Monsters.setValuesFromConfig(getConfig());
        itemStackSerializer = new ItemStackSerializer(this.getDataFolder().getPath());
        getCommand("ExChest").setExecutor(new ExChest(this));
        getCommand("Ex").setExecutor(new ExLootCommand(this));
        getCommand("Extier").setExecutor(new ExLootCommand(this));
        registerEvents();
        registerLoottable();
        databaseConnect();


        System.out.println(databaseManager.isConnected());
    }

    @Override
    public void onDisable() {
        databaseManager.disconnect();
    }



    private void registerEvents() {
        ChestPlaceListener chestPlaceListener = new ChestPlaceListener(this);
        LootViewListener lootViewListener = new LootViewListener(this);



        getServer().getPluginManager().registerEvents(chestPlaceListener, this);
        getServer().getPluginManager().registerEvents(lootViewListener, this);

    }

    public void databaseConnect() {
        databaseManager = new DatabaseManager(this);
        databaseManager.connect();

    }

    public void registerLoottable() {
        lootTableManager = new LootTableDataManager(this, this.getDataFolder().getPath());
    }

}
