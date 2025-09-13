package com.panita.panita275.end.util;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.qol.util.CustomItemManager;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DamageResistant;
import io.papermc.paper.datacomponent.item.Equippable;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.keys.SoundEventKeys;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DynamicItemCreation {
    public static void registerIfMissing() {
        createArmorPieceIfMissing(
                "dragonslayer_helmet",
                Material.IRON_HELMET,
                "<!italic><gradient:#A61AFC:#E43A96>Casco de Cazadragón</gradient>",
                List.of("&c❌ Visión Nocturna desactivada", "&7", "&dObtenido por participar en el evento de la", "&5Revancha contra el End &dde Panitacraft 2.75"),
                "panita:dragonslayer_helmet",
                EquipmentSlot.HEAD,
                6.0,
                4.0,
                0.1
        );

        createArmorPieceIfMissing(
                "dragonslayer_chestplate",
                Material.IRON_CHESTPLATE,
                "<!italic><gradient:#A61AFC:#E43A96>Pechera de Cazadragón</gradient>",
                List.of("&a✔ Inmunidad contra el Vacío activada", "&7", "&dObtenido por participar en el evento de la", "&5Revancha contra el End &dde Panitacraft 2.75"),
                "panita:dragonslayer_chestplate",
                EquipmentSlot.CHEST,
                10.0,
                4.0,
                0.1
        );

        createArmorPieceIfMissing(
                "dragonslayer_leggings",
                Material.IRON_LEGGINGS,
                "<!italic><gradient:#A61AFC:#E43A96>Pantalones de Cazadragón</gradient>",
                List.of("&7", "&dObtenido por participar en el evento de la", "&5Revancha contra el End &dde Panitacraft 2.75"),
                "panita:dragonslayer_leggings",
                EquipmentSlot.LEGS,
                8.0,
                4.0,
                0.1
        );

        createArmorPieceIfMissing(
                "dragonslayer_boots",
                Material.IRON_BOOTS,
                "<!italic><gradient:#A61AFC:#E43A96>Botas de Cazadragón</gradient>",
                List.of("&c❌ Doble Salto desactivado", "&7", "&dObtenido por participar en el evento de la", "&5Revancha contra el End &dde Panitacraft 2.75"),
                "panita:dragonslayer_boots",
                EquipmentSlot.FEET,
                6.0,
                4.0,
                0.1
        );
    }

    private static void createArmorPieceIfMissing(
            String key,
            Material material,
            String displayName,
            List<String> lore,
            String itemModel,
            EquipmentSlot slot,
            double armor,
            double toughness,
            double knockbackResistance
    ) {
        if (CustomItemManager.getItem(key) != null) return;

        ItemStack item = ItemStack.of(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.displayName(Messenger.mini(displayName));
        meta.lore(lore.stream().map(line -> Messenger.mini("<!italic>" + line)).toList());

        meta.setItemModel(NamespacedKey.fromString(itemModel));

        EquipmentSlotGroup attributeSlotGroup = switch (slot) {
            case HEAD -> EquipmentSlotGroup.HEAD;
            case CHEST -> EquipmentSlotGroup.CHEST;
            case LEGS -> EquipmentSlotGroup.LEGS;
            case FEET -> EquipmentSlotGroup.FEET;
            default -> EquipmentSlotGroup.ANY;
        };

        if (armor > 0) {
            meta.addAttributeModifier(Attribute.ARMOR,
                    new AttributeModifier(NamespacedKey.fromString(itemModel), armor, AttributeModifier.Operation.ADD_NUMBER, attributeSlotGroup));
        }
        if (toughness > 0) {
            meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS,
                    new AttributeModifier(NamespacedKey.fromString(itemModel), toughness, AttributeModifier.Operation.ADD_NUMBER, attributeSlotGroup));
        }
        if (knockbackResistance > 0) {
            meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(NamespacedKey.fromString(itemModel), knockbackResistance, AttributeModifier.Operation.ADD_NUMBER, attributeSlotGroup));
        }

        item.setItemMeta(meta);

        // Item Data Components
        Equippable.Builder equippableBuilder = item.getData(DataComponentTypes.EQUIPPABLE).toBuilder();
        equippableBuilder.assetId(Key.key("panita", "dragonslayer"));
        equippableBuilder.equipSound(SoundEventKeys.ENTITY_ENDER_DRAGON_FLAP);

        TagKey<DamageType> tagFire = TagKey.create(RegistryKey.DAMAGE_TYPE, Key.key("panitaend", "resists_fire_and_explosions"));

        item.setData(DataComponentTypes.MAX_DAMAGE, 2025);
        item.setData(DataComponentTypes.DAMAGE_RESISTANT, DamageResistant.damageResistant(tagFire));
        item.setData(DataComponentTypes.EQUIPPABLE, equippableBuilder.build());

        CustomItemManager.saveItem(key, item);
        Messenger.prefixedBroadcast("&dSe ha creado la pieza " + displayName + " para el set Cazadragón.");
    }
}
