package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OtherMobsDropPearl implements Listener {
    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.otherMobsDropPearl.enabled",
                ConfigDefaults.END_OTHER_MOBS_DROP_PEARLS_ENABLED)) return;

        List<String> mobString = Panitacraft.getConfigManager().getStringList("end.otherMobsDropPearl.mobs");

        List<EntityType> mobTypes = mobString.stream()
                .map(String::toUpperCase)
                .map(type -> {
                    try {
                        return EntityType.valueOf(type);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(type -> type != null && type != EntityType.ENDERMAN)
                .toList();

        if (!mobTypes.contains(event.getEntityType())) return;

        LivingEntity entity = event.getEntity();

        double dropChance = Panitacraft.getConfigManager().getDouble(
                "end.otherMobsDropPearl.chance",
                ConfigDefaults.END_OTHER_MOBS_DROP_PEARLS_CHANCE
        );

        int maxAmount = Panitacraft.getConfigManager().getInt(
                "end.otherMobsDropPearl.maxAmount",
                ConfigDefaults.END_OTHER_MOBS_DROP_PEARLS_MAX_AMOUNT
        );

        if (Math.random() < dropChance) {
            int amount = (int) Math.floor(Math.random() * maxAmount) + 1;
            event.getDrops().add(new ItemStack(Material.ENDER_PEARL, amount));
            event.setDroppedExp(135);
        }
    }
}
