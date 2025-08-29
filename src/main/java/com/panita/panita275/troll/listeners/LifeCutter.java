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

public class LifeCutter implements Listener {
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

        // Check custom_item_id key
        String customId = meta.getPersistentDataContainer()
                .get(CUSTOM_ID_KEY, PersistentDataType.STRING);
        if (customId == null || !customId.equals("life_cutter")) return;

        // Check if target is in affected players list
        if (!Panitacraft.getConfigManager().getStringList("troll.affectedPlayers").contains(target.getName())) return;

        // Force target to have half a heart
        event.setDamage(0);

        double maxHealth = Objects.requireNonNull(target.getAttribute(Attribute.MAX_HEALTH)).getValue();
        target.setHealth(Math.min(1.0, maxHealth));
    }
}
