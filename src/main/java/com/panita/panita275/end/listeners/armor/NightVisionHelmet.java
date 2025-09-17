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

import java.util.*;

public class NightVisionHelmet implements Listener {
    private final NamespacedKey nightVisionKey = new NamespacedKey("panita275", "night_vision");

    @EventHandler
    public void onHelmetClick(PlayerInteractEvent event) {
        // First check if the event has an item and if the action is a left click (in air or on block)
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK))
            return;

        // Then be sure the item is the Dragon Slayer Helmet
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item == null || item.getType() != Material.IRON_HELMET) return;
        if (!ArmorUtils.isDragonSlayerHelmet(item)) return;

        // Also be sure the player is actually holding the helmet in their main hand
        if (!item.isSimilar(player.getInventory().getItemInMainHand())) return;

        boolean active = item.getItemMeta().getPersistentDataContainer()
                .getOrDefault(nightVisionKey, PersistentDataType.BYTE, (byte) 0) != 0;

        boolean activate = !active;

        item.editMeta(meta -> {
            meta.getPersistentDataContainer().set(nightVisionKey, PersistentDataType.BYTE, (byte) (activate ? 1 : 0));

            List<Component> lore = meta.lore() != null ? new ArrayList<>(meta.lore()) : new ArrayList<>();
            Component helmetState = Messenger.mini(activate ? "&a✔ Visión Nocturna activada" : "&c❌ Visión Nocturna desactivada");

            if (!lore.isEmpty() && lore.getFirst().toString().contains("Visión Nocturna")) {
                lore.set(0, helmetState);
            } else {
                lore.addFirst(helmetState);
            }

            meta.lore(lore);
        });

        player.setCooldown(item, 10 * 20);
        SoundUtils.play(event.getPlayer(), "minecraft:block.note_block.bell", 1, 0);
    }
}
