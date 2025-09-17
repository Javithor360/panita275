package com.panita.panita275.end.listeners.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.panita.panita275.end.util.ArmorUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorEquip implements Listener {
    private final NamespacedKey nightVisionKey = new NamespacedKey("panita275", "night_vision");
    private final NamespacedKey speedKey = new NamespacedKey("panita275", "speed");

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        player.removePotionEffect(PotionEffectType.SPEED);

        if (!ArmorUtils.hasFullDragonSlayerSet(player)) return;

        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack leggings = player.getInventory().getLeggings();

        if (ArmorUtils.isDragonSlayerHelmet(helmet)) {
            boolean active = helmet.getItemMeta().getPersistentDataContainer()
                    .getOrDefault(nightVisionKey, PersistentDataType.BYTE, (byte) 0) != 0;
            if (active) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false, false));
            }
        }

        if (ArmorUtils.isDragonSlayerLeggings(leggings)) {
            boolean active = leggings.getItemMeta().getPersistentDataContainer()
                    .getOrDefault(speedKey, PersistentDataType.BYTE, (byte) 0) != 0;
            if (active) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1, false, false, false));
            }
        }
    }
}
