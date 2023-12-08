package me.logan.expedition.enums;

import java.util.ArrayList;
import java.util.List;

public enum Monsters {
    WOLF(40, 9, 1),
    SKELETON(25, 6, 1),

    CREEPER(18, 10, 2),
    SPIDER(12, 6, 2),

    WITCH(25, 8, 3),
    BLAZE(22, 9, 3),

    ENDERMAN(30, 12, 4),
    SLIME(25, 7, 4),

    WITHER_SKELETON(35, 10, 5),
    GHAST(28, 11, 5),

    IRON_GOLEM(40, 15, 6),
    WITHER(50, 20, 6);

    private final int Health;
    private final int damage;
    private final int tier;

    Monsters(int Health, int damage, int tier) {
        this.Health = Health;
        this.damage = damage;
        this.tier = tier;
    }



    public int getHealth() {
        return Health;
    }

    public int getDamage() {
        return damage;
    }

    public int getTier() {
        return tier;
    }


    public static List<Monsters> getMonstersFromTier(int tier) {
        List<Monsters> monsters = new ArrayList<>();
        for (Monsters monster : Monsters.values()) {
            if (monster.getTier() == tier) monsters.add(monster);
        }
        return monsters;
    }


}
