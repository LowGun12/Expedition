package me.logan.expedition.data;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class LootTableDataManager {

    private final JavaPlugin plugin;
    private final String path;
    private String fileName;

    public LootTableDataManager(JavaPlugin plugin, String path) {
        this.plugin = plugin;
        this.path = path;
        this.initialize();
    }

    public void initialize() {
        File folder = new File(this.path, "Loot Tables");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int i = 1; i <= 6; i++) {
            String fileName = i + ".json";
            File file = new File(folder, fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}