package com.panita.panita275.end.util;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.util.EntityUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnderMobFactory {
    private static final String ENDERMAN_SKULL_URL = "http://textures.minecraft.net/texture/7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf";

    public static Zombie createCustomZombie(Location loc, boolean powered) {
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        // Equipment
        ItemStack helmet = EntityUtils.createSkull(ENDERMAN_SKULL_URL);
        ItemStack chestplate, leggings, boots, sword;

        // Powered or not
        if (powered) {
            chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
            leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
            boots = new ItemStack(Material.NETHERITE_BOOTS);
            sword = new ItemStack(Material.NETHERITE_SWORD);

            chestplate.addEnchantment(Enchantment.PROTECTION, 3);
            leggings.addEnchantment(Enchantment.PROTECTION, 3);
            boots.addEnchantment(Enchantment.PROTECTION, 3);
            sword.addEnchantment(Enchantment.SHARPNESS, 4);

            zombie.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 1, false, true, false));
        } else {
            chestplate = createBlackArmor(Material.LEATHER_CHESTPLATE);
            leggings = createBlackArmor(Material.LEATHER_LEGGINGS);
            boots = createBlackArmor(Material.LEATHER_BOOTS);
            sword = new ItemStack(Material.DIAMOND_SWORD);
        }

        zombie.getEquipment().setHelmet(helmet);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setBoots(boots);
        zombie.getEquipment().setItemInMainHand(sword);

        zombie.customName(Messenger.mini("&dEnder Zombie"));
        setNoDrops(zombie);

        return zombie;
    }

    public static Skeleton createCustomSkeleton(Location loc, boolean powered) {
        Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);

        // Equipment
        ItemStack helmet = EntityUtils.createSkull(ENDERMAN_SKULL_URL);
        ItemStack chestplate, leggings, boots, bow;

        // Powered or not
        if (powered) {
            chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
            leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
            boots = new ItemStack(Material.NETHERITE_BOOTS);
            bow = new ItemStack(Material.BOW);

            chestplate.addEnchantment(Enchantment.PROTECTION, 3);
            leggings.addEnchantment(Enchantment.PROTECTION, 3);
            boots.addEnchantment(Enchantment.PROTECTION, 3);
            bow.addEnchantment(Enchantment.POWER, 4);
            bow.addEnchantment(Enchantment.PUNCH, 1);
        } else {
            chestplate = createBlackArmor(Material.LEATHER_CHESTPLATE);
            leggings = createBlackArmor(Material.LEATHER_LEGGINGS);
            boots = createBlackArmor(Material.LEATHER_BOOTS);
            bow = new ItemStack(Material.BOW);
        }

        skeleton.getEquipment().setHelmet(helmet);
        skeleton.getEquipment().setChestplate(chestplate);
        skeleton.getEquipment().setLeggings(leggings);
        skeleton.getEquipment().setBoots(boots);
        skeleton.getEquipment().setItemInMainHand(bow);

        skeleton.customName(Messenger.mini("&dEnder Skeleton"));
        setNoDrops(skeleton);

        return skeleton;
    }

    public static Creeper createCustomCreeper(Location loc, boolean powered) {
        Creeper creeper = (Creeper) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);

        creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1, true, false, false));
        creeper.addPotionEffect(new PotionEffect(PotionEffectType.OOZING, PotionEffect.INFINITE_DURATION, 1, true, false, false));
        creeper.setInvisible(true);
        creeper.customName(Messenger.mini("&dEnder Creeper"));
        creeper.getAttribute(Attribute.MAX_HEALTH).setBaseValue(powered ? 40 : 30);
        creeper.setHealth(powered ? 40 : 30);
        creeper.setExplosionRadius(powered ? 8 : 4);
        creeper.setPowered(powered);

        return creeper;
    }

    public static ItemStack createBlackArmor(Material material) {
        ItemStack item = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if (meta != null) {
            meta.setColor(Color.BLACK);
            item.setItemMeta(meta);
        }
        return item;
    }

    private static void setNoDrops(LivingEntity entity) {
        if (entity.getEquipment() == null) return;
        entity.getEquipment().setHelmetDropChance(0f);
        entity.getEquipment().setChestplateDropChance(0f);
        entity.getEquipment().setLeggingsDropChance(0f);
        entity.getEquipment().setBootsDropChance(0f);
        entity.getEquipment().setItemInMainHandDropChance(0f);
    }
}
