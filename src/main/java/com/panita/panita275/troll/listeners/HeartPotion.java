package com.panita.panita275.troll.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class HeartPotion implements Listener {
    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // Check if the item is the custom heart potion
        ItemStack registered = CustomItemManager.getItem("heart_potion");
        if (registered == null) return;

        ItemMeta regMeta = registered.getItemMeta();
        if (!meta.hasDisplayName() || !meta.getDisplayName().equals(regMeta.getDisplayName())) return;

        // Check if player is in affected players list
        boolean isAffected = Panitacraft.getConfigManager()
                .getStringList("troll.affectedPlayers")
                .contains(player.getName());

        if (isAffected) {
            // If so, increase max health by 1 heart (2 HP), up to a maximum of 10 hearts (20 HP)
            double maxHealth = Objects.requireNonNull(player.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();
            double newMaxHealth = Math.min(20.0, maxHealth + 2.0);
            player.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).setBaseValue(newMaxHealth);
            if (player.getHealth() < newMaxHealth) player.setHealth(Math.min(player.getHealth() + 2.0, newMaxHealth));
        } else {
            // If not, give them Health Boost II for 45 seconds
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 45 * 20, 0, false, true, false));
        }
    }
}
