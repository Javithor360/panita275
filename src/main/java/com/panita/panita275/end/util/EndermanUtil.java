package com.panita.panita275.end.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.util.EntityUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class EndermanUtil {
    public static void applyVariant(Enderman enderman) {
        double roll = Math.random();

        // Variant 1: 30% chance - Enderman 1.5 blocks height
        if (roll < 0.3) {
            enderman.customName(Messenger.mini("&dEnderman.ZIP"));
            enderman.getAttribute(Attribute.SCALE).setBaseValue(0.4);
            enderman.setSilent(false);
        }
        // Variant 2: 20% chance - Baby Zombie with Enderman Skin
        else if (roll < 0.5) {
            Zombie baby = (Zombie) enderman.getWorld().spawnEntity(enderman.getLocation(), EntityType.ZOMBIE);
            baby.setBaby();

            //
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

            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
            chestplateMeta.setColor(Color.BLACK);
            chestplate.setItemMeta(chestplateMeta);

            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
            leggingsMeta.setColor(Color.BLACK);
            leggings.setItemMeta(leggingsMeta);

            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
            bootsMeta.setColor(Color.BLACK);
            boots.setItemMeta(bootsMeta);

            //
            new BukkitRunnable() {
                @Override
                public void run() {
                    baby.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                    baby.getEquipment().setHelmet(skull);
                    baby.getEquipment().setChestplate(chestplate);
                    baby.getEquipment().setLeggings(leggings);
                    baby.getEquipment().setBoots(boots);

                    baby.getEquipment().setItemInMainHandDropChance(0f);
                    baby.getEquipment().setHelmetDropChance(0f);
                    baby.getEquipment().setChestplateDropChance(0f);
                    baby.getEquipment().setLeggingsDropChance(0f);
                    baby.getEquipment().setBootsDropChance(0f);
                }
            }.runTaskLater(Panitacraft.getInstance(), 1L);

            enderman.remove();
        }
        // Variant 3: 10% chance - Buffed Enderman Stats
        else if (roll < 0.6) {
            enderman.customName(Messenger.mini("&cEnderman Mamadísimo"));
            enderman.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(14.0);
            enderman.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(80.0);
            enderman.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.6);
            enderman.getAttribute(Attribute.MAX_HEALTH).setBaseValue(60.0);
            enderman.getAttribute(Attribute.SCALE).setBaseValue(1.5);
            enderman.setHealth(60.0);
            enderman.setSilent(false);
        } else {
            enderman.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(12.0);
        }
    }

    public static void makeHostile(Enderman enderman) {
        Player nearest = EntityUtils.getNearestPlayer(enderman.getLocation(), 64);

        if (nearest != null && nearest.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            enderman.setTarget(nearest);
            enderman.setAggressive(true);
        }

        EndermanManager.trackEnderman(enderman);
    }
}
