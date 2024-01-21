package me.logan.expedition.utils;

import me.logan.expedition.Expedition;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class NBTUtils {

    private final Expedition main;

    public NBTUtils(Expedition main) {
        this.main = main;
    }

    public void setNBTTag(LivingEntity entity, int tier) {
        NamespacedKey key = new NamespacedKey(main, "TierVal");
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.INTEGER, tier);
    }

    public Integer getNBTTag(LivingEntity entity) {
        NamespacedKey key = new NamespacedKey(main, "TierVal");

        return entity.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

}
