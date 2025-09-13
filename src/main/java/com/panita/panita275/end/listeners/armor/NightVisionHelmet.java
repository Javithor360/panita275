package com.panita.panita275.end.listeners.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.util.SoundUtils;
import com.panita.panita275.end.util.ArmorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class NightVisionHelmet implements Listener {
    private final NamespacedKey nightVisionKey = new NamespacedKey("panita275", "night_vision");

    @EventHandler
    public void onHelmetClick(PlayerInteractEvent event) {
        if (!event.hasItem() || !event.getAction().isLeftClick()) return;

        ItemStack item = event.getItem();
        if (item == null) return;
        if (!ArmorUtils.isDragonSlayerHelmet(item)) return;

        UUID playerId = event.getPlayer().getUniqueId();

        boolean active = false;
        if (item.getItemMeta().getPersistentDataContainer().has(nightVisionKey, PersistentDataType.BYTE)) {
            active = item.getItemMeta().getPersistentDataContainer().get(nightVisionKey, PersistentDataType.BYTE) != 0;
        }

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

        event.getPlayer().updateInventory();
        SoundUtils.play(event.getPlayer(), "minecraft:block.note_block.bell", 1, 0);
    }

    @EventHandler
    public void onHelmetEquip(PlayerArmorChangeEvent event) {
        ItemStack newItem = event.getNewItem();
        ItemStack oldItem = event.getOldItem();
        Player player = event.getPlayer();

        if (ArmorUtils.isDragonSlayerHelmet(oldItem)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }

        if (ArmorUtils.isDragonSlayerHelmet(newItem)) {
            boolean active = newItem.getItemMeta().getPersistentDataContainer()
                    .getOrDefault(nightVisionKey, PersistentDataType.BYTE, (byte) 0) != 0;
            if (active) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false, false));
            }
        }
    }
}
