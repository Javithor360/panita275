package com.panita.panita275.end.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
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

        LivingEntity entity;
        if (event.getEntity() instanceof Zombie) entity = (Zombie) event.getEntity();
        else if (event.getEntity() instanceof Skeleton) entity = (Skeleton) event.getEntity();
        else if (event.getEntity() instanceof Creeper) entity = (Creeper) event.getEntity();
        else {
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 2, false, false));
            return;
        }

        double chance = Panitacraft.getConfigManager().getDouble("end.pre-event.enderMobSpawnAmount", ConfigDefaults.END_PRE_EVENT_ENDER_MOB_SPAWN_AMOUNT);
        if (random.nextDouble() > chance) return;

        boolean isPowered = Panitacraft.getConfigManager().getBoolean("end.pre-event.poweredEnderMobs", ConfigDefaults.END_PRE_EVENT_POWERED_ENDER_MOBS);

        Location loc = entity.getLocation();

        LivingEntity customMob = (LivingEntity) loc.getWorld().spawnEntity(loc, entity.getType());

        if (!(customMob instanceof Creeper)) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

            //
            String url = "http://textures.minecraft.net/texture/7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf";
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
            profile.setProperty(new ProfileProperty("textures",
                    Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}").getBytes())
            ));
            skullMeta.setPlayerProfile(profile);
            skull.setItemMeta(skullMeta);

            ItemStack chestplate, leggings, boots, helmet;

            if (isPowered) {
                chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
                leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
                boots = new ItemStack(Material.NETHERITE_BOOTS);
                helmet = skull;

                chestplate.addEnchantment(Enchantment.PROTECTION, 3);
                leggings.addEnchantment(Enchantment.PROTECTION, 3);
                boots.addEnchantment(Enchantment.PROTECTION, 3);

                customMob.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 2, false, false));
            } else {
                chestplate = createBlackArmor(Material.LEATHER_CHESTPLATE);
                leggings = createBlackArmor(Material.LEATHER_LEGGINGS);
                boots = createBlackArmor(Material.LEATHER_BOOTS);
                helmet = skull;
            }

            customMob.getEquipment().setHelmet(helmet);
            customMob.getEquipment().setChestplate(chestplate);
            customMob.getEquipment().setLeggings(leggings);
            customMob.getEquipment().setBoots(boots);

            if (customMob instanceof Zombie) {
                ItemStack sword = new ItemStack(isPowered ? Material.NETHERITE_SWORD : Material.DIAMOND_SWORD);
                if (isPowered) sword.addEnchantment(Enchantment.SHARPNESS, 4);

                customMob.getEquipment().setItemInMainHand(sword);
                customMob.customName(Messenger.mini("&dEnder Zombie"));
            } else if (customMob instanceof Skeleton) {
                ItemStack bow = new ItemStack(Material.BOW);
                if (isPowered) bow.addEnchantment(Enchantment.POWER, 4);

                customMob.getEquipment().setItemInMainHand(bow);
                customMob.customName(Messenger.mini("&dEnder Skeleton"));
            }

            customMob.getEquipment().setHelmetDropChance(0f);
            customMob.getEquipment().setChestplateDropChance(0f);
            customMob.getEquipment().setLeggingsDropChance(0f);
            customMob.getEquipment().setBootsDropChance(0f);
            customMob.getEquipment().setItemInMainHandDropChance(0f);
            customMob.setRemoveWhenFarAway(true);

            event.setCancelled(true);
            entity.remove();

            return;
        }

        if (customMob instanceof Creeper creeper) {
            creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
            creeper.getAttribute(Attribute.MAX_HEALTH).setBaseValue(isPowered ? 40 : 30);
            creeper.setHealth(isPowered ? 40 : 30);
            creeper.customName(Messenger.mini("&dEnder Creeper"));
            creeper.setExplosionRadius(isPowered ? 8 : 4);
            creeper.setRemoveWhenFarAway(true);

            event.setCancelled(true);
            entity.remove();

            return;
        }
    }

    private ItemStack createBlackArmor(Material material) {
        ItemStack item = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if (meta != null) {
            meta.setColor(Color.BLACK);
            item.setItemMeta(meta);
        }
        return item;
    }
}
