package me.logan.expedition.utils;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ItemStackSerializer {

    private final Gson gson;
    private final String DataFolderPath;

    public ItemStackSerializer(String pluginDataFolderPath) {
        this.gson = new Gson();
        this.DataFolderPath = pluginDataFolderPath;
    }

    public void saveItemToJson(int tier, ItemStack itemStack) {
        Map<String, Object> serialized = serializeItemStack(itemStack);

        String fileName = tier + ".json";
        String filePath = DataFolderPath + File.separator + "Loot Tables" + File.separator + fileName;

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(serialized, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> serializeItemStack(ItemStack itemStack) {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("item", itemStack.serialize());
        return serialized;
    }

    public ItemStack[] deserializeItems(String json) {
        try {
            Gson gson = new Gson();
            Map<String, Object> serialized = gson.fromJson(json, Map.class);
            if (serialized != null && serialized.containsKey("item")) {
                Map<String, Object> itemMap = (Map<String, Object>) serialized.get("item");
                ItemStack itemStack = ItemStack.deserialize(itemMap);
                return new ItemStack[]{itemStack};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadLootGui(int tier, Player player) {
        File lootFolder = new File(DataFolderPath, "Loot Tables");
        if (!lootFolder.exists() || !lootFolder.isDirectory()) {
            return;
        }

        File lootFile = new File(lootFolder, tier + ".json");
        if (!lootFile.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(lootFile)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonContent.append(line);
            }
            // Deserialize JSON content to ItemStacks and add to inventory
            ItemStack[] items = deserializeItems(jsonContent.toString());
            Inventory lootInv = Bukkit.createInventory(null, 54, "Tier " + tier + " Loot");
            if (items != null) {
                lootInv.setContents(items);
            }
            player.openInventory(lootInv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLootToGui(int tier, Inventory inventory) {

        File lootFolder = new File(DataFolderPath, "Loot Tables");
        if (!lootFolder.exists() || !lootFolder.isDirectory()) {
            return;
        }

        File lootFile = new File(lootFolder, tier + ".json");
        if (!lootFile.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(lootFile)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonContent.append(line);
            }
            ItemStack[] items = deserializeItems(jsonContent.toString());
            if (items != null) {
                inventory.setContents(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ItemStack generateLoot(int tier, Player player) {
        File lootFolder = new File(DataFolderPath, "Loot Tables");
        if (!lootFolder.exists() || !lootFolder.isDirectory()) {
            return null;
        }

        File lootFile = new File(lootFolder, tier + ".json");

        if (!lootFile.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(lootFile)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonContent.append(line);
            }

            ItemStack[] items = deserializeItems(jsonContent.toString());
            if (items != null && items.length > 0) {
                Random random = new Random();
                ItemStack randomItem = items[random.nextInt(items.length)];
                return randomItem;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

