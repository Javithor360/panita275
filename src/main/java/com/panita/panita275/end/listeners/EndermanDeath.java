package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EndermanDeath implements Listener {
    @EventHandler
    public void onEndermanDeath(EntityDeathEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.pre-event.enderMobSpawn", ConfigDefaults.END_PRE_EVENT_ENDER_MOB_SPAWN)) return;

        double chance = Panitacraft.getConfigManager().getDouble("end.pre-event.endermalKillSpawnPhantomChance", 0.75);

        if (chance > 0 && Math.random() < chance) {
            if (event.getEntity() instanceof Enderman enderman) {
                Location loc = enderman.getLocation();
                Phantom phantom = (Phantom) loc.getWorld().spawnEntity(loc, EntityType.PHANTOM);
                phantom.setTarget(event.getEntity().getKiller());
            }
        }
    }
}
