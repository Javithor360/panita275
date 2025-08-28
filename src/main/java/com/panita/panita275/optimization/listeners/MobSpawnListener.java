package com.panita.panita275.optimization.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean(
                "optimization.mobSpawning.reduceWolfs",
                ConfigDefaults.OPTIMIZATION_MOB_SPAWNING_REDUCEWOLFS)) return;
        LivingEntity entity = event.getEntity();

        if (entity instanceof Wolf wolf) {
            if (entity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL && wolf.isAware() && !wolf.isTamed()
                    & wolf.getAge() == 0 && wolf.isAdult() && !wolf.isSitting()) {
                event.setCancelled(true);

                if (Panitacraft.getConfigManager().getBoolean("optimization.debug", ConfigDefaults.OPTIMIZATION_DEBUG)) {
                    Messenger.prefixedBroadcast("&7[Debug] &aSe ha cancelado el spawn de un lobo por cumplir las condiciones.");
                }
            }
        }
    }
}
