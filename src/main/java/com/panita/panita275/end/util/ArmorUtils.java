package com.panita.panita275.end.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorUtils {
    private static final NamespacedKey HELMET = NamespacedKey.fromString("panita:dragonslayer_helmet");
    private static final NamespacedKey CHEST = NamespacedKey.fromString("panita:dragonslayer_chestplate");
    private static final NamespacedKey LEGS = NamespacedKey.fromString("panita:dragonslayer_leggings");
    private static final NamespacedKey BOOTS = NamespacedKey.fromString("panita:dragonslayer_boots");

    /**
     * Check if the player is wearing the full Dragon Slayer armor set.
     * @param player the player to check
     * @return true if the player is wearing the full set, false otherwise
     */
    public static boolean hasFullDragonSlayerSet(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chest = player.getInventory().getChestplate();
        ItemStack legs = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        return hasModel(helmet, HELMET)
                && hasModel(chest, CHEST)
                && hasModel(legs, LEGS)
                && hasModel(boots, BOOTS);
    }

    public static boolean hasDragonSlayerSet(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack legs = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        return hasModel(helmet, HELMET)
                && hasModel(legs, LEGS)
                && hasModel(boots, BOOTS);
    }

    /**
     * Check if the item is the Dragon Slayer Helmet.
     * @param item the item to check
     * @return true if the item is the Dragon Slayer Helmet, false otherwise
     */
    public static boolean isDragonSlayerHelmet(ItemStack item) {
        return hasModel(item, HELMET);
    }

    /**
     * Check if the item is the Dragon Slayer Chestplate.
     * @param item the item to check
     * @return true if the item is the Dragon Slayer Chestplate, false otherwise
     */
    public static boolean isDragonSlayerChestplate(ItemStack item) {
        return hasModel(item, CHEST);
    }

    /**
     * Check if the item is the Dragon Slayer Leggings.
     * @param item the item to check
     * @return true if the item is the Dragon Slayer Leggings, false otherwise
     */
    public static  boolean isDragonSlayerLeggings(ItemStack item) {
        return hasModel(item, LEGS);
    }

    /**
     * Check if the item is the Dragon Slayer Boots.
     * @param item the item to check
     * @return true if the item is the Dragon Slayer Boots, false otherwise
     */
    public static boolean isDragonSlayerBoots(ItemStack item) {
        return hasModel(item, BOOTS);
    }

    private static boolean hasModel(ItemStack item, NamespacedKey modelKey) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = meta.getItemModel();
        return key != null && key.equals(modelKey);
    }
}
