package com.panita.panita275.optimization.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.entity.Bee;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class MobSpawnListener implements Listener {
    private boolean isInDatapackStructure(LivingEntity entity, String namespace) {
        var worldHandle = ((CraftWorld) entity.getWorld()).getHandle();
        var pos = new BlockPos(entity.getLocation().getBlockX(),
                entity.getLocation().getBlockY(),
                entity.getLocation().getBlockZ());
        var structureManager = worldHandle.getStructureManager();

        for (ResourceKey<Structure> key : Registry.STRUCTURE.keySet()) {
            if (!key.location().getNamespace().equals(namespace)) continue;

            StructureStart start = structureManager.getStructureStart(pos, key);
            if (start != null && start.isValid()) return true;
        }
        return false;
    }

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
