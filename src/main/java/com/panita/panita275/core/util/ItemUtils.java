package com.panita.panita275.core.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static boolean checkItemModel(ItemStack item, NamespacedKey modelKey) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = meta.getItemModel();
        return key != null && key.equals(modelKey);
    }

    public static NamespacedKey getItemModel(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        return meta.getItemModel();
    }
}
