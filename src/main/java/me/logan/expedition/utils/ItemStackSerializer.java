package me.logan.expedition.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.xml.crypto.Data;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ItemStackSerializer {

    private final Gson gson;
    private final String DataFolderPath;

    public ItemStackSerializer(String pluginDataFolderPath) {
        this.gson = new Gson();
        this.DataFolderPath = pluginDataFolderPath;
    }

    public void saveItemsToJson(int tier, List<ItemStack> itemStackList) {
        List<Map<String, Object>> serializedList = new ArrayList<>();

        // Serialize each ItemStack in the list
        for (ItemStack itemStack : itemStackList) {
            Map<String, Object> serializedItem = serializeItemStack(itemStack);
            serializedList.add(serializedItem);
        }

        String fileName = tier + ".json";
        String filePath = DataFolderPath + File.separator + "Loot Tables" + File.separator + fileName;

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(serializedList, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> serializeItemStack(ItemStack itemStack) {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("item", itemStack.serialize());
        return serialized;
    }

    private ItemStack deserializeItemStack(Map<String, Object> serializedItem) {
        ItemStack itemStack = ItemStack.deserialize((Map<String, Object>) serializedItem.get("item"));
        return itemStack;
    }

    public List<ItemStack> loadItemsFromJson(int tier) {
        List<ItemStack> itemStackList = new ArrayList<>();

        String fileName = tier + ".json";
        String filePath = DataFolderPath + File.separator + "Loot Tables" + File.separator + fileName;

        try (FileReader fileReader = new FileReader(filePath)) {
            // Parse JSON file into a list of maps
            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            List<Map<String, Object>> serializedList = gson.fromJson(fileReader, listType);

            // Deserialize each map into ItemStack and add to the list
            for (Map<String, Object> serializedItem : serializedList) {
                ItemStack itemStack = deserializeItemStack(serializedItem);
                itemStackList.add(itemStack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemStackList;
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

        // Deserialize JSON content to ItemStacks and add to inventory
        List<ItemStack> items = loadItemsFromJson(tier);
        System.out.println("Items are: " + items);

        Inventory lootInv = Bukkit.createInventory(null, 54, "Tier " + tier + " Loot");
        if (items != null) {
            for (ItemStack item : items) {
                lootInv.addItem(item);
            }
        }

        player.openInventory(lootInv);
    }

    public void addLootToGui(int tier, Inventory inventory) {
        List<ItemStack> items = loadItemsFromJson(tier);

        if (items != null) {
            inventory.clear(); // Clear existing contents before adding new items
            for (ItemStack item : items) {
                inventory.addItem(item);
            }
        }
    }


}

