package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
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
        if (!Panitacraft.getConfigManager().getBoolean("end.phantomDrops.enabled", ConfigDefaults.END_PHANTOM_DROPS_ENABLED)) return;
        if (!(event.getEntity() instanceof Phantom)) return;

        double ghastTearChance = Panitacraft.getConfigManager().getDouble("end.phantomDrops.ghastTearChance", ConfigDefaults.END_PHANTOM_DROPS_GHAST_TEAR_CHANCE);
        int maxCoal = Panitacraft.getConfigManager().getInt("end.phantomDrops.maxCoalAmount", ConfigDefaults.END_PHANTOM_DROPS_MAX_COAL_AMOUNT);
        int maxTears = Panitacraft.getConfigManager().getInt("end.phantomDrops.maxGhastTearAmount", ConfigDefaults.END_PHANTOM_DROPS_MAX_GHAST_TEAR_AMOUNT);
        int expDrop = Panitacraft.getConfigManager().getInt("end.phantomDrops.expDropped", ConfigDefaults.END_PHANTOM_DROPS_EXP_DROPPED);

        // 20% chance to drop a ghast tear
        if (random.nextDouble() < ghastTearChance) {
            event.getDrops().add(new ItemStack(Material.GHAST_TEAR, maxTears));
        }

        // Always drop 1 coal
        event.getDrops().add(new ItemStack(Material.COAL, maxCoal));

        // Custom experience drop
        event.setDroppedExp(expDrop);
    }
}
