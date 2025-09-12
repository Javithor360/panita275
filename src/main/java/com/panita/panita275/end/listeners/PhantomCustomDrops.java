package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import org.bukkit.Material;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PhantomCustomDrops implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onPhantomDeath(EntityDeathEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.phantomDrops.enabled", false)) return;
        if (!(event.getEntity() instanceof Phantom)) return;

        double ghastTearChance = Panitacraft.getConfigManager().getDouble("end.phantomDrops.ghastTearChance", 0.2);
        int maxCoal = Panitacraft.getConfigManager().getInt("end.phantomDrops.maxCoalAmount", 3);
        int expDrop = Panitacraft.getConfigManager().getInt("end.phantomDrops.expDropped", 50);

        // 20% chance to drop a ghast tear
        if (random.nextDouble() < ghastTearChance) {
            event.getDrops().add(new ItemStack(Material.GHAST_TEAR, 1));
        }

        // Always drop 1 coal
        event.getDrops().add(new ItemStack(Material.COAL, maxCoal));

        // Custom experience drop
        event.setDroppedExp(expDrop);
    }
}
