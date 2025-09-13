package com.panita.panita275.end.util;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.core.util.EntityUtils;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class MobSpawnManager {
    private static final Random random = new Random();

    public static void handleHoglinSpawn(Hoglin hoglin) {
        if (!Panitacraft.getConfigManager().getBoolean(
                "end.pre-event.increaseEndermanSpawn",
                ConfigDefaults.END_PRE_EVENT_INCREASE_ENDERMAN_SPAWN)) return;

        double hostileChance = Panitacraft.getConfigManager().getDouble(
                "end.pre-event.endermanHostileChance",
                ConfigDefaults.END_ENDERMAN_HOSTILE_CHANCE);

        Enderman enderman = (Enderman) hoglin.getWorld().spawnEntity(hoglin.getLocation().add(0, 2, 0), EntityType.ENDERMAN);
        hoglin.addPassenger(enderman);

        if (random.nextDouble() < hostileChance) {
            EndermanUtil.makeHostile(enderman);
        }
    }

    public static void handleEnderMobInTheEnd(Enderman enderman, CreatureSpawnEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean(
                "end.event.enderMobs.enabled",
                ConfigDefaults.END_EVENT_ENDER_MOBS_ENABLED)) return;

        double chance = Panitacraft.getConfigManager().getDouble(
                "end.event.enderMobs.spawnAmount",
                ConfigDefaults.END_EVENT_ENDER_MOB_SPAWN_AMOUNT);

        if (random.nextDouble() >= chance) return;

        enderman.remove();
        event.setCancelled(true);

        double roll = random.nextDouble();

        if (roll < 0.4) {
            EnderMobFactory.createCustomZombie(enderman.getLocation(), true);
        } else if (roll < 0.8) {
            EnderMobFactory.createCustomSkeleton(enderman.getLocation(), true);
        } else {
            EnderMobFactory.createCustomCreeper(enderman.getLocation(), true);
        }
    }

    public static void handleEnderMobsInOverworld(LivingEntity entity, CreatureSpawnEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean(
                "end.pre-event.enderMobSpawn",
                ConfigDefaults.END_PRE_EVENT_ENDER_MOB_SPAWN)) return;

        if (entity.getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;

        double chance = Panitacraft.getConfigManager().getDouble(
                "end.pre-event.enderMobSpawnAmount",
                ConfigDefaults.END_PRE_EVENT_ENDER_MOB_SPAWN_AMOUNT);

        if (random.nextDouble() > chance) return;

        entity.remove();
        event.setCancelled(true);

        boolean isPowered = Panitacraft.getConfigManager().getBoolean(
                "end.pre-event.poweredEnderMobs",
                ConfigDefaults.END_PRE_EVENT_POWERED_ENDER_MOBS);

        if (entity instanceof Zombie) {
            EnderMobFactory.createCustomZombie(entity.getLocation(), isPowered);
        } else if (entity instanceof Skeleton) {
            EnderMobFactory.createCustomSkeleton(entity.getLocation(), isPowered);
        } else if (entity instanceof Creeper) {
            EnderMobFactory.createCustomCreeper(entity.getLocation(), isPowered);
        }
    }

    public static void handleEndermanSpawn(Enderman enderman, CreatureSpawnEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean(
                "end.pre-event.increaseEndermanSpawn",
                ConfigDefaults.END_PRE_EVENT_INCREASE_ENDERMAN_SPAWN)) return;

        double chance = Panitacraft.getConfigManager().getDouble(
                "end.pre-event.endermanSpawnAmount",
                ConfigDefaults.END_PRE_EVENT_ENDERMAN_SPAWN_AMOUNT);
        double hostileChance = Panitacraft.getConfigManager().getDouble(
                "end.pre-event.endermanHostileChance",
                ConfigDefaults.END_ENDERMAN_HOSTILE_CHANCE);

        if (random.nextDouble() >= chance) return;

        if (!EntityUtils.isEnoughSpaceY(enderman.getLocation(), 3, 0)) return;

        event.setCancelled(true);
        Enderman spawned = (Enderman) enderman.getLocation().getWorld().spawnEntity(enderman.getLocation(), EntityType.ENDERMAN);

        Messenger.broadcast("&dCheck ");

        if (random.nextDouble() < hostileChance) {
            EndermanUtil.makeHostile(spawned);
        } else {
            EndermanUtil.applyVariant(spawned);
        }
    }
}
