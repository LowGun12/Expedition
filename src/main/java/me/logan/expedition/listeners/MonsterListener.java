package me.logan.expedition.listeners;

import me.logan.expedition.Expedition;
import me.logan.expedition.utils.NBTUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MonsterListener implements Listener {

    private final Expedition main;

    public MonsterListener(Expedition main) {
        this.main = main;
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity killedEntity = e.getEntity();

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
}


