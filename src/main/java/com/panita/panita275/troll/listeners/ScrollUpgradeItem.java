package com.panita.panita275.troll.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.util.Global;
import com.panita.panita275.core.util.SoundUtils;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ScrollUpgradeItem implements Listener {

    @EventHandler
    public void onScrollUse(PlayerInteractEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("troll.enderUpgrades.enabled", false)) return;
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (!CustomItemManager.isCustomItem(item)) return;

        // Get Item ID in metadata
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(Panitacraft.getInstance(), "custom_item_id");
        String itemId = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (itemId == null) return;

        // Check if items section exists
        ConfigurationSection itemsSection = Panitacraft.getConfigManager().getSection("troll.enderUpgrades.items");
        if (itemsSection == null) return;

        // Ensure the itemId exists in the config
        ConfigurationSection scrollSection = itemsSection.getConfigurationSection(itemId);
        if (scrollSection == null) return;

        // Check player permissions
        String requiredPerm = scrollSection.getString("permission", "");
        if (!Global.hasPermission(player, requiredPerm)) {
            Messenger.prefixedSend(player, "&cNo cumples los requisitos para usar este pergamino.");
            return;
        }

        // Execute commands parsing %player%
        List<String> commands = Panitacraft.getConfigManager().getStringList("troll.enderUpgrades.items." + itemId + ".commands");
        for (String cmd : commands) {
            String parsed = cmd.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsed);
        }

        // Send message and play sound if configured
        String msg = scrollSection.getString("message.text", "");
        if (!msg.isEmpty()) {
            Messenger.prefixedSend(player, msg);
        }

        String sound = scrollSection.getString("message.sound", "");
        if (!sound.isEmpty()) {
            SoundUtils.play(player, sound, 1.0f, 1.0f);
        }

        // Consume item
        item.setAmount(item.getAmount() - 1);
    }
}
