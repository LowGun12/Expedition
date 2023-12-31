package me.logan.expedition.utils;

import me.logan.expedition.Expedition;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemStackSerializer {

    private Expedition main;

    public ItemStackSerializer(Expedition main) {
        this.main = main;
    }
    private static final Logger logger = Logger.getLogger(ItemStackSerializer.class.getName());

    public byte[] serializeItemStack(ItemStack itemStack) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        dataOutput.writeObject(itemStack);
        dataOutput.close();

        return outputStream.toByteArray();
        }

    public static ItemStack deserializeItemStack(String serializedItem) {
        try {
            byte[] data = Base64.getDecoder().decode(serializedItem);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack itemStack = (ItemStack) dataInput.readObject();
            dataInput.close();
            return itemStack;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while deserializing ItemStack", e);
            return null;
        }
    }

    public void saveItemToJson(int tier, ItemStack item) {
        String filePath = tier + ".json";
        File file = new File(main.getDataFolder(), filePath);

        try {
            byte[] serializedItemStack = serializeItemStack(item);

            // Save the serialized ItemStack to the JSON file
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(serializedItemStack);
            fileOutputStream.close();

            Bukkit.getLogger().info("ItemStack saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadItemToGui(int tier) {

    }
}

