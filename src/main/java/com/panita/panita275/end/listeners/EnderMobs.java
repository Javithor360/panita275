package com.panita.panita275.end.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.end.util.EnderMobFactory;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class EnderMobs implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.pre-event.enderMobSpawn", ConfigDefaults.END_PRE_EVENT_ENDER_MOB_SPAWN)) return;
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;
        if (!event.getEntity().getWorld().getEnvironment().equals(World.Environment.NORMAL)) return;

        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Zombie || entity instanceof Skeleton || entity instanceof Creeper)) {
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 2, false, false)); return;
        }

        double chance = Panitacraft.getConfigManager().getDouble("end.pre-event.enderMobSpawnAmount", ConfigDefaults.END_PRE_EVENT_ENDER_MOB_SPAWN_AMOUNT);
        if (random.nextDouble() > chance) return;

        event.setCancelled(true);
        entity.remove();

        boolean isPowered = Panitacraft.getConfigManager().getBoolean("end.pre-event.poweredEnderMobs", ConfigDefaults.END_PRE_EVENT_POWERED_ENDER_MOBS);

        switch (entity) {
            case Zombie zombie -> EnderMobFactory.createCustomZombie(entity.getLocation(), isPowered);
            case Skeleton skeleton -> EnderMobFactory.createCustomSkeleton(entity.getLocation(), isPowered);
            case Creeper creeper -> EnderMobFactory.createCustomCreeper(entity.getLocation(), isPowered);
            default -> {
            }
        }
    }
}
