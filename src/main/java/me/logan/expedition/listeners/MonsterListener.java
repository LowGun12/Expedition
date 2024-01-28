package me.logan.expedition.listeners;

import me.logan.expedition.Expedition;
import me.logan.expedition.utils.ItemStackSerializer;
import me.logan.expedition.utils.NBTUtils;
import me.logan.expedition.utils.PlayerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MonsterListener implements Listener {

    private final Expedition main;
    private final ItemStackSerializer itemStackSerializer;

    public MonsterListener(Expedition main) {
        this.main = main;
        this.itemStackSerializer = new ItemStackSerializer(main.getDataFolder().getPath());
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity killedEntity = e.getEntity();


        NBTUtils nbtUtils = new NBTUtils(main);
        Integer tier = nbtUtils.getNBTTag(killedEntity);
        Integer nbtValue = nbtUtils.getNBTTag(killedEntity);
        System.out.println("NBTTag value for entity: " + nbtValue);

        if (tier != null) {
            // The killed entity has a valid "TierVal" NBTTag
            generateLoot(tier, killedEntity.getKiller());
        }
    }


    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) e.getEntity();

            NBTUtils nbtUtils = new NBTUtils(main);
            Integer tag = nbtUtils.getNBTTag(le);

            if (tag != null) {

            } else {
                return;
            }
        }
    }



    public void generateLoot(int tier, Player player) {
        List<ItemStack> lootList = itemStackSerializer.loadItemsFromJson(tier);

        if (lootList != null && !lootList.isEmpty()) {
            // Generate a random index to pick a random item from the lootList
            int randomIndex = (int) (Math.random() * lootList.size());
            ItemStack randomLoot = lootList.get(randomIndex).clone();

            // Give the generated loot to the player
            PlayerUtils.addItemToPlayer(player, randomLoot);

            // You can also notify the player about the received loot
            player.sendMessage("You have received a random piece of loot!");
        } else {
            // Handle the case when there is no loot for the specified tier
            player.sendMessage("No loot available for Tier " + tier);
        }
    }
}


