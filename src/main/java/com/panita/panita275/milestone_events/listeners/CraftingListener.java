package com.panita.panita275.milestone_events.listeners;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.milestone_events.model.EventType;
import com.panita.panita275.milestone_events.util.CraftingUtils;
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
            if (event.getRecipe() == null || event.getRecipe().getResult() == null) return;

            String craftedType = event.getRecipe().getResult().getType().toString().toLowerCase();
            String configItem = e.getCraftingItem().toLowerCase();

            // remove "minecraft:" prefix if present
            if (configItem.startsWith("minecraft:")) {
                configItem = configItem.substring("minecraft:".length());
            }

            if (!craftedType.equalsIgnoreCase(configItem)) return;

            if (event.getWhoClicked() instanceof Player player) {
                // Call CraftingUtils to get the amount crafted
                int amount = CraftingUtils.getCraftedAmount(event);
                if (amount > 0) {
                    EventProgressManager.addProgress(e.getName(), player.getName(), amount, e);
                    e.updateBossBar();
                }
            }
        });
    }
}
