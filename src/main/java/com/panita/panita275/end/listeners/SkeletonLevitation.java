package com.panita.panita275.end.listeners;

import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import com.panita.panita275.Panitacraft;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class SkeletonLevitation implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onSkeletonHit(EntityDamageByEntityEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.pre-event.enderMobSpawn", ConfigDefaults.END_PRE_EVENT_ENDER_MOB_SPAWN)) return;
        if (!(event.getEntity() instanceof Player player)) return;

        if (!(event.getDamager() instanceof Projectile projectile)) return;
        if (!(projectile.getShooter() instanceof Skeleton)) return;

        double chance = Panitacraft.getConfigManager().getDouble(
                "end.skeletonLevitationChance", 0.2
        );

        if (chance > 0 && random.nextDouble() <= chance) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5 * 20, 2, false, true));
        }
    }
}
