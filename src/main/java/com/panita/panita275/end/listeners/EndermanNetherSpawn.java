package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.core.util.EntityUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EndermanNetherSpawn implements Listener {
    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        // First check if the feature is enabled in the config
        if (!Panitacraft.getConfigManager().getBoolean("end.pre-event.netherDimension.increaseEndermanSpawn",
                ConfigDefaults.END_PRE_EVENT_NETHER_DIMENSION_INCREASE_ENDERMAN_SPAWN)) return;

        // Check if the spawn is happening in the Nether and is a natural spawn
        if (event.getEntity().getWorld().getEnvironment() != World.Environment.NETHER) return;
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;
        if (event.getEntityType() == EntityType.ENDERMAN) return;

        double chance = Panitacraft.getConfigManager().getDouble("end.pre-event.netherDimension.endermanSpawnAmount",
                ConfigDefaults.END_PRE_EVENT_NETHER_DIMENSION_ENDERMAN_SPAWN_AMOUNT);

        // First we check the possibility of Hoglin
        if (event.getEntityType() == EntityType.HOGLIN) {
            // Spawn an Enderman above the Hoglin
            Hoglin hoglin = (Hoglin) event.getEntity();
            Location spawnLoc = hoglin.getLocation().add(0, 2, 0); // 2 blocks above the Hoglin

            // Prepare the Enderman to spawn
            Enderman enderman = (Enderman) hoglin.getWorld().spawnEntity(spawnLoc, EntityType.ENDERMAN);

            hoglin.addPassenger(enderman);

            // Make the Enderman aggressive towards the nearest player
            Player nearestPlayer = EntityUtils.getNearestPlayer(hoglin.getLocation(), 64);

            // If a player is found, set the Enderman's target to that player
            if (nearestPlayer != null) {
                enderman.setAggressive(true);
                enderman.setTarget(nearestPlayer);
            }

            return; // return early since we've already spawned an Enderman
        }

        // Evaluate the random chance to determine whether to spawn an Enderman
        if (Math.random() < chance) {
            Location loc = event.getLocation();

            // Check if there's enough space for an Enderman (3 blocks high)
            if (EntityUtils.isEnoughSpaceY(loc, 3, 0)) {
                event.setCancelled(true);
                loc.getWorld().spawnEntity(loc, EntityType.ENDERMAN);
            }
        }
    }
}
