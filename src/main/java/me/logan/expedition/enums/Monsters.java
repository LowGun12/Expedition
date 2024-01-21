package me.logan.expedition.enums;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public enum Monsters {

    WOLF,
    SKELETON,
    CREEPER,
    SPIDER,
    WITCH,
    BLAZE,
    ENDERMAN,
    SLIME,
    WITHER_SKELETON,
    SILVERFISH,
    IRON_GOLEM,
    WITHER;

    private int Health;
    private int Damage;
    private int Tier;

    Monsters() {
        // Default values
        Health = 20;
        Damage = 5;
        Tier = 1;
    }

    public int getHealth() {
        return Health;
    }

    public int getDamage() {
        return Damage;
    }

    public int getTier() {
        return Tier;
    }

    public static void setValuesFromConfig(FileConfiguration config) {
        for (Monsters monster : values()) {
            ConfigurationSection section = config.getConfigurationSection("monsters." + monster.name());

            if (section != null) {
                monster.Health = section.getInt("Health", 20);
                monster.Damage = section.getInt("Damage", 5);
                monster.Tier = section.getInt("Tier", 1);
            }
        }
    }

    public static List<Monsters> getMonstersFromTier(int tier) {
        List<Monsters> monsters = new ArrayList<>();
        for (Monsters monster : values()) {
            if (monster.getTier() == tier) {
                monsters.add(monster);
            }
        }
        return monsters;
    }
}