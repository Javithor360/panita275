package com.panita.panita275.troll.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.qol.util.CustomItemManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HeartStealer implements Listener {
    private static final NamespacedKey CUSTOM_ID_KEY =
            new NamespacedKey(Panitacraft.getInstance(), "custom_item_id");

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;
        if (!(event.getEntity() instanceof Player target)) return;
        if (!Panitacraft.getConfigManager().getBoolean("troll.enabled", ConfigDefaults.TROLL_ENABLED)) return;

        ItemStack item = damager.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // Check CustomItemId
        String customId = meta.getPersistentDataContainer()
                .get(CUSTOM_ID_KEY, PersistentDataType.STRING);
        if (customId == null || !customId.equals("heart_stealer")) return;

        // Check if target is in affected players list
        if (!Panitacraft.getConfigManager().getStringList("troll.affectedPlayers").contains(target.getName()))
            return;

        // Cancel damage
        event.setDamage(0);

        // Reduce 1 heart (2 HP) from max health
        AttributeInstance attr = target.getAttribute(Attribute.MAX_HEALTH);
        if (attr == null) return;

        double baseHealth = attr.getBaseValue();
        double newBaseHealth = Math.max(2.0, baseHealth - 2.0);
        attr.setBaseValue(newBaseHealth);

        // Adjust current health if it exceeds new max health
        if (target.getHealth() > newBaseHealth) {
            target.setHealth(newBaseHealth);
        }

        double targetHealth = target.getHealth();
        if (targetHealth > 2.0) {
            ItemStack heartPotion = CustomItemManager.getItem("heart_potion");
            if (heartPotion != null) {
                heartPotion = heartPotion.clone();
                ItemMeta potionMeta = heartPotion.getItemMeta();
                if (potionMeta != null) {
                    List<Component> lore = potionMeta.hasLore() ? new ArrayList<>(potionMeta.lore()) : new ArrayList<>();
                    lore.add(Messenger.mini("&7"));
                    lore.add(Messenger.mini("<gold>Corazón enfrascado de " + target.getName() + "</gold>"));
                    potionMeta.lore(lore);
                    heartPotion.setItemMeta(potionMeta);
                }

                Location dropLoc = target.getLocation().add(0, 1, 0);
                target.getWorld().dropItemNaturally(dropLoc, heartPotion);
            }
        }
    }
}
