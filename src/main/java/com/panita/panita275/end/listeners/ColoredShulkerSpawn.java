package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class ColoredShulkerSpawn implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onShulkerSpawn(CreatureSpawnEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("event.shulkers.variations", ConfigDefaults.END_EVENT_SHULKERS_COLORED)) return;
        if (event.getEntityType() != EntityType.SHULKER) return;

        Shulker shulker = (Shulker) event.getEntity();
        DyeColor color = DyeColor.values()[random.nextInt(DyeColor.values().length)];

        shulker.setColor(color);
    }
}
