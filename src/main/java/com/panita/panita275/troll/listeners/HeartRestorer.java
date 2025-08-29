package com.panita.panita275.troll.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class HeartRestorer implements Listener {

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

        // Verify CustomItemId
        String customId = meta.getPersistentDataContainer()
                .get(CUSTOM_ID_KEY, PersistentDataType.STRING);
        if (customId == null || !customId.equals("heart_restorer")) return;

        // Cancel damage
        event.setDamage(0);

        // Increase max health by 1 heart (2 HP), up to a maximum of 10 hearts (20 HP)
        double maxHealth = Objects.requireNonNull(target.getAttribute(Attribute.MAX_HEALTH)).getValue();
        double newMaxHealth = Math.min(20.0, maxHealth + 2.0);
        target.getAttribute(Attribute.MAX_HEALTH).setBaseValue(newMaxHealth);

        // Adjust current health if below new max health
        if (target.getHealth() < newMaxHealth) {
            target.setHealth(Math.min(newMaxHealth, target.getHealth() + 2.0));
        }
    }
}
