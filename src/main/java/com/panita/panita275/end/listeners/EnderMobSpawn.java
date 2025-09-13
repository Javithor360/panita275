package com.panita.panita275.end.listeners;

import com.panita.panita275.end.util.MobSpawnManager;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class EnderMobSpawn implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        World.Environment dim = event.getEntity().getWorld().getEnvironment();
        Entity entity = event.getEntity();

        if (entity.getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;

        if (dim == World.Environment.NETHER && entity instanceof Hoglin hoglin) {
            MobSpawnManager.handleHoglinSpawn(hoglin);
            return;
        }

        if (dim == World.Environment.NORMAL && !(entity instanceof Enderman)) {
            MobSpawnManager.handleEnderMobsInOverworld((LivingEntity) entity, event);
            return;
        }

        if (dim == World.Environment.THE_END && entity instanceof Enderman enderman) {
            MobSpawnManager.handleEnderMobInTheEnd(enderman, event);
        }

        if (entity instanceof Enderman enderman) {
            MobSpawnManager.handleEndermanSpawn(enderman, event);
        }
    }
}
