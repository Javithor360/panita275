package com.panita.panita275.troll.util;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.List;

public class DynamicItemCreation {

    public static void registerIfMissing() {
        createCustomItemIfMissing(
                "life_cutter",
                Material.SHEARS,
                "<!italic><gradient:#FC1A2C:#B15960>Falso Tortazo</gradient>",
                List.of(
                        "&7Un ítem malévolo utilizado como método",
                        "&7de tortura para aquellos que han cometido",
                        "&7un grave delito."
                )
        );

        createCustomItemIfMissing(
                "heart_stealer",
                Material.LEAD,
                "<!italic><gradient:#FC1A2C:#B15960>Látigo Cruel</gradient>",
                List.of(
                        "&7Un ítem malévolo utilizado como método",
                        "&7de tortura para aquellos que han cometido",
                        "&7un grave delito."
                )
        );
        
        createCustomItemIfMissing(
                "heart_restorer",
                Material.BRUSH,
                "<!italic><gradient:#FC1A2C:#B15960>Peluza Amorosa</gradient>",
                List.of(
                        "&7Un ítem benévolo utilizado como método",
                        "&7de perdón para aquellos que han cometido",
                        "&7un grave delito."
                )
        );

        createCustomItemIfMissing(
                "heart_restorer",
                Material.BRUSH,
                "<!italic><gradient:#FC1A2C:#B15960>Peluza Amorosa</gradient>",
                List.of(
                        "&7Un ítem benévolo utilizado como método",
                        "&7de perdón para aquellos que han cometido",
                        "&7un grave delito."
                )
        );

        createHeartPotionIfMissing();

        createDetailedCustomItemIfMissing(
                "golden_beetroot",
                Material.BEETROOT,
                "<!italic><gradient:#FDBF1C:#F3FFA9:#FDBF1C>Remolacha Dorada</gradient>",
                List.of(
                        "&7",
                        "&9No se consume al comerla.", "&7",
                        "&9Ten &esiempre &9al menos &edos unidades",
                        "&een el inventario &9para que funcione", "&7",
                        "&cNo se puede cultivar.", "&7",
                        "&dEste ítem es de origen &4paradójico",
                        "&dconseguido de manera &5ludopática",
                        "&ddurante Panitacraft 2.75.", "&7"
                ),
                "panita:beetroot_dorada",
                true,
                true
        );

        createDetailedCustomItemIfMissing(
                "infinite_golden_apple",
                Material.APPLE,
                "<!italic><gradient:#FDBF1C:#F3FFA9:#FDBF1C>Manzana Doradísima</gradient>",
                List.of(
                        "&7",
                        "&9No se consume al comerla.", "&7",
                        "&9Ten &esiempre &9al menos &edos unidades",
                        "&een el inventario &9para que funcione", "&7",
                        "&dEste ítem es de origen &4paradójico",
                        "&dconseguido de manera &5ludopática",
                        "&ddurante Panitacraft 2.75.", "&7"
                ),
                "minecraft:golden_apple",
                true,
                true
        );

        createDetailedCustomItemIfMissing(
                "scroll_upgrade_enderchest_4",
                Material.PAPER,
                "<!italic><#9863E7>Pergamo de Mejora: Enderchest IV",
                List.of("&8&oAl consumirlo, aumenta el espacio de", "&8tu Enderchest.", "&7", "&dConseguido de manera &5ludopática", "&ddurante Panitacraft 2.75."),
                "panita:scroll_upgrade",
                false,
                false
        );

        createDetailedCustomItemIfMissing(
                "scroll_upgrade_enderchest_5",
                Material.PAPER,
                "<!italic><#9863E7>Pergamo de Mejora: Enderchest V",
                List.of("&8&oAl consumirlo, aumenta el espacio de", "&8&otu Enderchest. Pero, necesario haber", "&8&oconsumido un pergamino de &7&oNivel IV", "&8antes de activa la mejora.", "&7", "&dConseguido de manera &5ludopática", "&ddurante Panitacraft 2.75."),
                "panita:scroll_upgrade",
                false,
                false
        );

        createDetailedCustomItemIfMissing(
                "scroll_upgrade_enderchest_6",
                Material.PAPER,
                "<!italic><#9863E7>Pergamo de Mejora: Enderchest VI",
                List.of("&8&oAl consumirlo, aumenta el espacio de", "&8&otu Enderchest. Pero, necesario haber", "&8&oconsumido un pergamino de &7&oNivel V", "&8antes de activa la mejora.", "&7", "&dConseguido de manera &5ludopática", "&ddurante Panitacraft 2.75."),
                "panita:scroll_upgrade",
                false,
                false
        );

        createDetailedCustomItemIfMissing(
                "scroll_upgrade_enderchest_command",
                Material.PAPER,
                "<!italic><#9863E7>Pergamo de Mejora: Enderchest Inalámbrico",
                List.of("&8&oAl consumirlo, permite abrir tu Enderchest", "&8desde cualquier lugar", "&7", "&dConseguido de manera &5ludopática", "&ddurante Panitacraft 2.75."),
                "panita:scroll_upgrade",
                false,
                false
        );

        createDetailedCustomItemIfMissing(
                "colytra_upgrade_smithing_template",
                Material.PAPER,
                "<!italic><#9863E7>Mejora de Colytra",
                List.of("&9Materiales", "&7Plantilla de herrería", "&7", "&7Se aplica a:", " &9Peto de diamante y netherite", "&7Ingredientes:", " &9Elytra"),
                "panita:colytra_upgrade_smithing_template",
                false,
                false
        );

        createDetailedCustomItemIfMissing(
                "enderslayer_colytra_upgrade_smithing_template",
                Material.PAPER,
                "<!italic><gradient:#A61AFC:#E43A96>Mejora de Colytra de Cazadragón</gradient>",
                List.of("&9Materiales", "&7Plantilla de herrería", "&7", "&7Se aplica a:", " &9Peto de Cazadragón", "&7Ingredientes:", " &9Elytra de Cazadragón"),
                "panita:colytra_upgrade_smithing_template",
                false,
                false
        );

        createDetailedCustomItemIfMissing(
                "infinite_water_bucket",
                Material.WATER_BUCKET,
                "<!italic><gradient:#5FE2C5:#4498DB>Cubo de Agua Infinito</gradient>",
                List.of(
                        "&7",
                        "&9No se vacía al utilizarla.", "&7",
                        "&9Ten &esiempre &9al menos &edos unidades",
                        "&een el inventario &9para que funcione", "&7",
                        "&dEste ítem es de origen &4paradójico",
                        "&dconseguido de manera &5ludopática",
                        "&ddurante Panitacraft 2.75.", "&7"
                ),
                null,
                true,
                true
        );

        createDetailedCustomItemIfMissing(
                "infinite_torch",
                Material.TORCH,
                "<!italic><gradient:#5FE2C5:#4498DB>Antorcha Infinita</gradient>",
                List.of(
                        "&7",
                        "&9No se consume al colocarla.", "&7",
                        "&dEste ítem es de origen &4paradójico",
                        "&dconseguido de manera &5ludopática",
                        "&ddurante Panitacraft 2.75.", "&7"
                ),
                null,
                true,
                true
        );

        createDetailedCustomItemIfMissing(
                "infinite_wind_charge",
                Material.WIND_CHARGE,
                "<!italic><gradient:#5FE2C5:#4498DB>Wind Charge Infinita</gradient>",
                List.of(
                        "&7",
                        "&9No se consume al utilizarla.", "&7",
                        "&9Ten &esiempre &9al menos &edos unidades",
                        "&een el inventario &9para que funcione", "&7",
                        "&dEste ítem es de origen &4paradójico",
                        "&dconseguido de manera &5ludopática",
                        "&ddurante Panitacraft 2.75.", "&7"
                ),
                null,
                true,
                true
        );

        createDetailedCustomItemIfMissing(
                "infinite_firework_rocket",
                Material.FIREWORK_ROCKET,
                "<!italic><gradient:#5FE2C5:#4498DB>Cohete de Fuegos Artificiales Infinito</gradient>",
                List.of(
                        "&7",
                        "&9No se consume al utilizarlo.", "&7",
                        "&9Ten &esiempre &9al menos &edos unidades",
                        "&een el inventario &9para que funcione", "&7",
                        "&dEste ítem es de origen &4paradójico",
                        "&dconseguido de manera &5ludopática",
                        "&ddurante Panitacraft 2.75.", "&7"
                ),
                null,
                true,
                true
        );
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

    private static void createDetailedCustomItemIfMissing(
            String key,
            Material material,
            String displayName,
            List<String> lore,
            String itemModel,
            boolean unbreakable,
            boolean glint
    ) {
        if (CustomItemManager.getItem(key) != null) return;

        ItemStack item = ItemStack.of(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.displayName(Messenger.mini(displayName));
        meta.lore(lore.stream().map(line -> Messenger.mini("<!italic>" + line)).toList());

        if (itemModel != null && !itemModel.isEmpty()) {
            meta.setItemModel(NamespacedKey.fromString(itemModel));
        }

        if (unbreakable) {
            meta.setUnbreakable(true);
        }

        if (glint) {
            meta.setEnchantmentGlintOverride(true);
        }

        if (key.equals("infinite_firework_rocket")) {
            FireworkMeta fireworkMeta = (FireworkMeta) meta;
            fireworkMeta.setPower(1);
        }

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
