package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ShulkerBulletEffect implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onShulkerBulletHit(ProjectileHitEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.shulkers.variations", ConfigDefaults.END_PHANTOM_DROPS_ENABLED)) return;
        if (event.getEntity().getWorld().getEnvironment() != World.Environment.THE_END) return;

        if (!(event.getEntity() instanceof ShulkerBullet)) return;
        if (!(event.getHitEntity() instanceof LivingEntity target)) return;

        if (random.nextDouble() < 0.2) {
            int level = 2 + random.nextInt(9);
            int duration = 5 + random.nextInt(10);
            target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, duration * 20, level - 1));
        } else if (random.nextDouble() < 0.3) {
            Location original = target.getLocation();

            double offsetX = -10 + random.nextDouble() * 20;
            double offsetY = 0;
            double offsetZ = -10 + random.nextDouble() * 20;

            Location newLoc = original.clone().add(offsetX, offsetY, offsetZ);
            target.teleport(newLoc);
        } else if (random.nextDouble() < 0.35) {
            Location loc = target.getLocation();
            Phantom phantom = (Phantom) loc.getWorld().spawnEntity(loc, EntityType.PHANTOM);
            phantom.setTarget(target);
        } else return;
    }
}
