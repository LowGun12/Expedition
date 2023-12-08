package me.logan.expedition.listeners;

import me.logan.expedition.Expedition;
import me.logan.expedition.enums.Monsters;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ChestPlaceListener implements Listener {

    private final Expedition main;

    public ChestPlaceListener(Expedition main) {
        this.main = main;
    }

    @EventHandler
    public void onChestPlace(BlockPlaceEvent e){

        ItemStack item = e.getItemInHand();

        if (item != null && item.getType() == Material.CHEST) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                Bukkit.getLogger().info("Placed tiered chest");
                NamespacedKey key = new NamespacedKey(main, "tier");
                Integer tier = meta .getPersistentDataContainer().get(key, PersistentDataType.INTEGER);

                if (tier != null) {
                    Bukkit.getLogger().info("Chest has a tier");
                    Location chestLocation = e.getBlock().getLocation();


                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            spawnMonsters(chestLocation, tier);
                        }
                    }.runTaskTimer(main, 0, 100);
                }

            }
        }


    }

    private void spawnMonsters(Location location, int tier) {
        List<Monsters> monsters = Monsters.getMonstersFromTier(tier);

        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            Location spawnLocation = location.clone().add(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);

            if (spawnLocation.getBlock().getType().isSolid()) {
                continue;
            }
            Monsters monster = monsters.get(random.nextInt(monsters.size()));
            LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(spawnLocation, EntityType.valueOf(monster.name()));

            entity.setMaxHealth(monster.getHealth());
            entity.setHealth(monster.getHealth());
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(monster.getDamage());
        }
    }
}
