package com.panita.panita275.milestone_events.listeners;

import com.panita.panita275.milestone_events.model.EventType;
import com.panita.panita275.milestone_events.util.CraftingUtils;
import com.panita.panita275.milestone_events.util.EventProgressManager;
import com.panita.panita275.milestone_events.util.MilestoneEventManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        MilestoneEventManager.getActiveEvents().forEach(e -> {
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
                ItemStack result = event.getRecipe().getResult();
                int amount = CraftingUtils.getCraftedAmount(event);
                if (amount <= 0) return;

                int maxAddable = getMaxAddable(player, result, amount);
                if (maxAddable <= 0) return;

                EventProgressManager.addProgress(e.getName(), player.getName(), maxAddable, e);
            }
        });
    }

    private int getMaxAddable(Player player, ItemStack item, int attemptAmount) {
        int maxStack = item.getMaxStackSize();
        int remaining = attemptAmount;

        // Cursor
        ItemStack cursor = player.getOpenInventory().getCursor();
        if (cursor != null && cursor.getType() == item.getType()) {
            remaining = Math.min(remaining, maxStack - cursor.getAmount());
            if (remaining <= 0) return 0;
        }

        // Inventory
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (remaining <= 0) break;
            if (invItem == null || invItem.getType() == Material.AIR) {
                remaining -= Math.min(remaining, maxStack);
            } else if (invItem.getType() == item.getType()) {
                remaining -= Math.min(remaining, maxStack - invItem.getAmount());
            }
        }

        return attemptAmount - remaining;
    }

}
