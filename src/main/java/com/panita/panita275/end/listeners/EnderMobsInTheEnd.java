package com.panita.panita275.end.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.end.util.EnderMobFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class EnderMobsInTheEnd implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onTheEndMobSpawn(CreatureSpawnEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.event.enderMobs.enabled", ConfigDefaults.END_EVENT_ENDER_MOBS_ENABLED)) return;
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL && !event.getEntity().getWorld().getEnvironment().equals(World.Environment.THE_END)) return;
        if (!(event.getEntity() instanceof Enderman enderman)) return;

        double chance = Panitacraft.getConfigManager().getDouble("end.event.enderMobs.spawnAmount", ConfigDefaults.END_EVENT_ENDER_MOB_SPAWN_AMOUNT);
        if (random.nextDouble() > chance) return;

        event.setCancelled(true);
        Random mobRandom = new Random();
        double roll = mobRandom.nextDouble();
        if (roll < 0.4) {
            EnderMobFactory.createCustomZombie(enderman.getLocation(), true);
        } else if (roll < 0.8) {
            EnderMobFactory.createCustomSkeleton(enderman.getLocation(), true);
        } else {
            EnderMobFactory.createCustomCreeper(enderman.getLocation(), true);
        }

        enderman.remove();
        event.setCancelled(true);
    }
}
