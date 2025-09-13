package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class BlazeProjectileListener implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.blazeFireball.spawnEscort", ConfigDefaults.END_BLAZE_FIREBALL_SPAWN_ESCORT)) return;

        Projectile proj = event.getEntity();
        if (!(proj instanceof Fireball || proj instanceof SmallFireball)) return;

        if (!(proj.getShooter() instanceof Blaze)) return;

        Location loc = proj.getLocation();
        World world = loc.getWorld();
        if (world == null || !world.getEnvironment().equals(World.Environment.NETHER)) return;

        double chance = Panitacraft.getConfigManager().getDouble("end.pre-event.fireballSpawnBlazeChance", ConfigDefaults.END_BLAZE_FIREBALL_ESCORT_CHANCE);
        if (chance > 0 && random.nextDouble() > chance) return;

        Phantom phantom = (Phantom) world.spawnEntity(loc, EntityType.PHANTOM);
        phantom.setSize(1);
        phantom.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));

        WitherSkeleton ws = (WitherSkeleton) world.spawnEntity(loc, EntityType.WITHER_SKELETON);
        ws.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 1, false, false));
        ws.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        ws.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
        ws.getEquipment().setItemInMainHandDropChance(0F);
        ws.getEquipment().setChestplateDropChance(0F);
        ws.customName(Messenger.mini("&dEscolta de Blaze"));

        phantom.addPassenger(ws);
    }
}
