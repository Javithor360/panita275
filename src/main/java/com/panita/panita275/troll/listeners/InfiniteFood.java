package com.panita.panita275.troll.listeners;

import com.panita.panita275.core.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InfiniteFood implements Listener {

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        ItemStack itemStack = event.getItem();
        Player player = event.getPlayer();

        NamespacedKey item = ItemUtils.getItemModel(event.getItem());

        if (item == null || itemStack.getAmount() < 2) return;

        switch (item.getKey()) {
            case "beetroot_dorada" -> {
                event.setCancelled(true);
                player.getInventory().setItemInMainHand(itemStack);

                player.setFoodLevel(Math.min(20, player.getFoodLevel() + 7));
                player.setSaturation(Math.min(20, player.getSaturation() + 12));
            }
            case "golden_apple" -> {
                if (event.getItem().getType() != Material.APPLE) return;

                event.setCancelled(true);
                player.getInventory().setItemInMainHand(itemStack);

                player.setFoodLevel(Math.min(20, player.getFoodLevel() + 4));
                player.setSaturation(Math.min(20, player.getSaturation() + 9.6f));

                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60 * 20, 0, false, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 15 * 20, 0, false, true, true));
            }
            default -> {
                return;
            }
        }
    }
}
