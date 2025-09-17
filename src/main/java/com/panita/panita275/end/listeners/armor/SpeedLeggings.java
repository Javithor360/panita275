package com.panita.panita275.end.listeners.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.util.SoundUtils;
import com.panita.panita275.end.util.ArmorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SpeedLeggings implements Listener {
    private final NamespacedKey speedKey = new NamespacedKey("panita275", "speed");

    @EventHandler
    public void onLeggingsClick(PlayerInteractEvent event) {
        // First check if the event has an item and if the action is a left click (in air or on block)
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK))
            return;

        // Then be sure the item is the Dragon Slayer Leggings
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item == null || item.getType() != Material.IRON_LEGGINGS) return;
        if (!ArmorUtils.isDragonSlayerLeggings(item)) return;

        // Also be sure the player is actually holding the helmet in their main hand
        if (!item.isSimilar(player.getInventory().getItemInMainHand())) return;

        boolean active = item.getItemMeta().getPersistentDataContainer()
                .getOrDefault(speedKey, PersistentDataType.BYTE, (byte) 0) != 0;

        boolean activate = !active;

        item.editMeta(meta -> {
            meta.getPersistentDataContainer().set(speedKey, PersistentDataType.BYTE, (byte) (activate ? 1 : 0));

            List<Component> lore = meta.lore() != null ? new ArrayList<>(meta.lore()) : new ArrayList<>();
            Component leggingsState = Messenger.mini(activate ? "&a✔ Velocidad Aumentada activada" : "&c❌ Velocidad Aumentada desactivada");

            if (!lore.isEmpty() && lore.get(0).toString().contains("Velocidad Aumentada")) {
                lore.set(0, leggingsState);
            } else {
                lore.add(0, leggingsState);
            }

            meta.lore(lore);
        });

        player.setCooldown(item, 10 * 20);
        SoundUtils.play(event.getPlayer(), "minecraft:block.note_block.bell", 1, 0);
    }

    @EventHandler
    public void onLeggingsEquip(PlayerArmorChangeEvent event) {
        ItemStack newItem = event.getNewItem();
        ItemStack oldItem = event.getOldItem();
        Player player = event.getPlayer();

        if (ArmorUtils.isDragonSlayerLeggings(oldItem)) {
            player.removePotionEffect(PotionEffectType.SPEED);
        }

        if (ArmorUtils.isDragonSlayerLeggings(newItem) && ArmorUtils.hasDragonSlayerSet(player)) {
            boolean active = newItem.getItemMeta().getPersistentDataContainer()
                    .getOrDefault(speedKey, PersistentDataType.BYTE, (byte) 0) != 0;

            if (active) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1, false, false, false));
            }
        }
    }
}
