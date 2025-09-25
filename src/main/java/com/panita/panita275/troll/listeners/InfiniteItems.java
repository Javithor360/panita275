package com.panita.panita275.troll.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.util.ItemUtils;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InfiniteItems implements Listener {

    private boolean hasAtLeastTwo(Player player, Material material) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material && CustomItemManager.isCustomItem(item)) {
                count += item.getAmount();
                if (count >= 2) return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onWaterBucketUse(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        EquipmentSlot hand = event.getHand();
        ItemStack itemInHand = (hand == EquipmentSlot.HAND)
                ? player.getInventory().getItemInMainHand()
                : player.getInventory().getItemInOffHand();
        if (itemInHand == null || !CustomItemManager.isCustomItem(itemInHand, "infinite_water_bucket")) return;
        if (player.hasCooldown(itemInHand)) return;

        event.setCancelled(true);

        Block clicked = event.getBlockClicked();
        if (clicked == null) return;
        Block target = clicked.getRelative(event.getBlockFace());
        target.setType(Material.WATER);

        target.setType(Material.WATER);

        ItemStack infiniteBucket = CustomItemManager.getItem("infinite_water_bucket");
        if (hand == EquipmentSlot.HAND) {
            player.getInventory().setItemInMainHand(infiniteBucket);
        } else {
            player.getInventory().setItemInOffHand(infiniteBucket);
        }

        player.updateInventory();
        player.setCooldown(infiniteBucket, 5 * 20);
    }

    @EventHandler
    public void onTorchPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (!CustomItemManager.isCustomItem(item, "infinite_torch")) return;

        Player player = event.getPlayer();
        if (!hasAtLeastTwo(player, Material.TORCH)) return;
        if (player.hasCooldown(item)) return;

        int before = item.getAmount();
        Bukkit.getScheduler().runTaskLater(Panitacraft.getInstance(), () -> {
            item.setAmount(before);
            player.updateInventory();
            player.setCooldown(item, 5 * 20);
        }, 1L);
    }

    @EventHandler
    public void onWindChargeUse(PlayerInteractEvent event) {
        if (event.getItem() == null) return;

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack item = event.getItem();
        if (item.getType() != Material.WIND_CHARGE) return;
        if (!CustomItemManager.isCustomItem(item, "infinite_wind_charge")) return;

        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || !hasAtLeastTwo(player, Material.WIND_CHARGE)) return;
        if (player.hasCooldown(item)) return;

        item.setAmount(item.getAmount() + 1);
        player.updateInventory();
    }

    /**
     * Handles the use of infinite firework rockets.
     * @param event The PlayerInteractEvent triggered when a player interacts.
     */
    @EventHandler
    public void onFireworkUse(PlayerInteractEvent event) {
        if (event.getItem() == null) return;

        // Only proceed if the action is a right-click (either in air or on a block)
        Action action = event.getAction();
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) return;

        // Ensue the item is a firework rocket and is the custom infinite firework rocket
        ItemStack item = event.getItem();
        if (item.getType() != Material.FIREWORK_ROCKET) return;
        if (!CustomItemManager.isCustomItem(item, "infinite_firework_rocket")) return;

        // Check if the player has at least two firework rockets in their inventory
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || !hasAtLeastTwo(player, Material.FIREWORK_ROCKET)) return;
        if (player.hasCooldown(item)) return;

        // Replenish the firework rocket used based on the action type
        if (action == Action.RIGHT_CLICK_BLOCK) {
            item.setAmount(item.getAmount() + 1);

            // Apply a short cooldown to prevent rapid usage
            Bukkit.getScheduler().runTaskLater(Panitacraft.getInstance(), () -> {
                player.setCooldown(Material.FIREWORK_ROCKET, 3 * 20);
            }, 1L);
            return;
        }

        if (action == Action.RIGHT_CLICK_AIR && player.isGliding()) {
            item.setAmount(item.getAmount() + 1); // Replenish just if the player is gliding

            Bukkit.getScheduler().runTaskLater(Panitacraft.getInstance(), () -> {
                player.setCooldown(Material.FIREWORK_ROCKET, 3 * 20);
            }, 1L);
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        ItemStack itemStack = event.getItem();
        Player player = event.getPlayer();

        NamespacedKey item = ItemUtils.getItemModel(event.getItem());

        if (item == null || !hasAtLeastTwo(player, itemStack.getType())) return;

        switch (item.getKey()) {
            case "beetroot_dorada" -> {
                event.setCancelled(true);

                itemStack.setAmount(itemStack.getAmount() + 1);

                player.setFoodLevel(Math.min(20, player.getFoodLevel() + 5));
                player.setSaturation(Math.min(20, player.getSaturation() + 8.5f));
            }
            case "golden_apple" -> {
                if (event.getItem().getType() != Material.APPLE) return;

                event.setCancelled(true);

                itemStack.setAmount(itemStack.getAmount() + 1);

                player.setFoodLevel(Math.min(20, player.getFoodLevel() + 3));
                player.setSaturation(Math.min(20, player.getSaturation() + 7.6f));

                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60 * 20, 0, false, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 15 * 20, 0, false, true, true));
            }
            default -> {
                return;
            }
        }
    }
}
