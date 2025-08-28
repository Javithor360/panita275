package com.panita.panita275.milestone_events.listeners;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.milestone_events.model.EventType;
import com.panita.panita275.milestone_events.util.EventProgressManager;
import com.panita.panita275.milestone_events.util.MilestoneEventManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftingListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        MilestoneEventManager.getActiveEvent().ifPresent(e -> {
            if (e.getType() != EventType.CRAFTING) return;
            if (event.getCurrentItem() == null) return;

            String itemName = event.getCurrentItem().getType().toString().toLowerCase();

            String configItem = e.getCraftingItem().toLowerCase();
            if (configItem.startsWith("minecraft:")) {
                configItem = configItem.substring("minecraft:".length());
            }

            if (!itemName.equalsIgnoreCase(configItem)) return;

            if (event.getWhoClicked() instanceof Player player) {
                int craftedAmount = event.getCurrentItem().getAmount();
                Messenger.prefixedBroadcast("Items crafted: " + craftedAmount + " x " + itemName + " by " + player.getName());
                EventProgressManager.addProgress(e.getName(), player.getName(), craftedAmount, e);
                e.updateBossBar();
            }
        });
    }
}
