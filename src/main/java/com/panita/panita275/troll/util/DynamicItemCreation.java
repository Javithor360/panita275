package com.panita.panita275.troll.util;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class DynamicItemCreation {

    public static void registerIfMissing() {
        createCustomItemIfMissing(
                "life_cutter",
                Material.SHEARS,
                "<gradient:#FC1A2C:#B15960>Falso Tortazo</gradient>",
                List.of(
                        "&7Un ítem malévolo utilizado como método",
                        "&7de tortura para aquellos que han cometido",
                        "&7un grave delito."
                )
        );

        createCustomItemIfMissing(
                "heart_stealer",
                Material.LEAD,
                "<gradient:#FC1A2C:#B15960>Látigo Cruel</gradient>",
                List.of(
                        "&7Un ítem malévolo utilizado como método",
                        "&7de tortura para aquellos que han cometido",
                        "&7un grave delito."
                )
        );
        
        createCustomItemIfMissing(
                "heart_restorer",
                Material.BRUSH,
                "<gradient:#FC1A2C:#B15960>Peluza Amorosa</gradient>",
                List.of(
                        "&7Un ítem benévolo utilizado como método",
                        "&7de perdón para aquellos que han cometido",
                        "&7un grave delito."
                )
        );

        createCustomItemIfMissing(
                "heart_restorer",
                Material.BRUSH,
                "<gradient:#FC1A2C:#B15960>Peluza Amorosa</gradient>",
                List.of(
                        "&7Un ítem benévolo utilizado como método",
                        "&7de perdón para aquellos que han cometido",
                        "&7un grave delito."
                )
        );

        createHeartPotionIfMissing();
    }

    private static void createCustomItemIfMissing(
            String key,
            Material material,
            String displayName,
            List<String> lore
    ) {
        if (CustomItemManager.getItem(key) != null) return;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.displayName(Messenger.mini(displayName));
        meta.lore(lore.stream().map(Messenger::mini).toList());

        item.setItemMeta(meta);
        CustomItemManager.saveItem(key, item);

        Messenger.prefixedBroadcast("&aSe ha creado el item " + displayName + " para el modulo troll.");
    }

    private static void createHeartPotionIfMissing() {
        if (CustomItemManager.getItem("heart_potion") != null) return;

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        if (meta == null) return;

        meta.displayName(Messenger.mini("<gradient:#FC1A2C:#B15960>Poción del Corazón</gradient>"));
        List<String> lore = List.of(
                "&7Un ítem benévolo que proporciona un rayo de",
                "&7esperanza a la persona que lo ingiera."
        );
        meta.lore(lore.stream().map(Messenger::mini).toList());

        meta.setColor(Color.fromRGB(255, 0, 0));
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP); // deprecated
        potion.setItemMeta(meta);

        CustomItemManager.saveItem("heart_potion", potion);
        Messenger.prefixedBroadcast("&aSe ha creado el item Poción del Corazón para el módulo troll.");
    }
}
