package com.panita.panita275.troll.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.util.ItemUtils;
import com.panita.panita275.core.util.SoundUtils;
import com.panita.panita275.qol.util.CustomItemManager;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class RechargeableFirework implements Listener {
    private static final NamespacedKey ROCKET_LEVEL_KEY = new NamespacedKey(Panitacraft.getInstance(), "rechargeable_level");

    /**
     * This handles the logic to recharge a firework rocket
     * based on some conditions related to the player state
     *
     * @param event
     */
    @EventHandler
    public void onRechargeFirework(PlayerInteractEvent event) {
        if (event.getItem() == null) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        // Just execute if the player right clicks the air and is sneaking and on the ground
        if (!(action == Action.RIGHT_CLICK_AIR)) return;
        if (!player.isSneaking() || !player.isOnGround()) return;

        ItemStack item = event.getItem();
        if (!CustomItemManager.isCustomItem(item, "rechargeable_firework")) return;

        // Retrieve the specific data components
        int maxEnergy = item.getData(DataComponentTypes.MAX_DAMAGE);
        int currentDamage = item.getData(DataComponentTypes.DAMAGE);
        int currentEnergy = maxEnergy - currentDamage; // Energy is the inverse of damage

        if (currentEnergy >= maxEnergy) {
            Messenger.prefixedSend(player, "&7Tu cohete ya está recargado a su máxima capacidad.");
            return;
        }

        // Then calculate how much gunpowder we can consume and update the item
        int needed = maxEnergy - currentEnergy;
        int gunpowderInInv = ItemUtils.countMaterial(player, Material.GUNPOWDER);
        int toConsume = Math.min(needed, gunpowderInInv);

        if (toConsume <= 0) {
            Messenger.prefixedSend(player, "&7No tienes pólvora para recargar tu cohete.");
            return;
        }

        // Consume the gunpowder and update the item's damage
        ItemUtils.removeMaterial(player, Material.GUNPOWDER, toConsume);

        // Update the item's damage to reflect the new energy level
        int newDamage = maxEnergy - (currentEnergy + toConsume);
        item.setData(DataComponentTypes.DAMAGE, newDamage);
        Messenger.prefixedSend(player, "&7Has recargado tu cohete con &b" + toConsume + " &7de pólvora. Energía actual: &b" + (currentEnergy + toConsume) + "/" + maxEnergy);
    }

    /**
     * This handles the logic to change the firework rocket level
     * changing dynamically its power and lore, also adding a cooldown
     * to prevent spam clicking
     */
    @EventHandler
    public void onRechargeableRocketLevelChange(PlayerInteractEvent event) {
        if (event.getItem() == null) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        // The condition here is that the player just left clicks the air
        // and is not sneaking or gliding
        if (action != Action.LEFT_CLICK_AIR) return;
        if (player.isSneaking() || player.isGliding()) return;

        // Ensure the item is a rechargeable firework rocket and the player has no
        // cooldown and is not in creative
        ItemStack item = event.getItem();
        if (!CustomItemManager.isCustomItem(item, "rechargeable_firework")) return;
        if (player.hasCooldown(item) || player.getGameMode() == GameMode.CREATIVE) return;

        // Then get the current level, and retrieve the persistent data container
        FireworkMeta meta = (FireworkMeta) item.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        int currentLevel = container.getOrDefault(ROCKET_LEVEL_KEY, PersistentDataType.INTEGER, 1);
        int nextLevel = (currentLevel % 5) + 1; // Cycle through levels 1 to 5

        container.set(ROCKET_LEVEL_KEY, PersistentDataType.INTEGER, nextLevel); // Update the level in the persistent data

        // Update the firework power and lore based on the new level
        meta.setPower(nextLevel);

        List<Component> lore = meta.lore() != null ? new ArrayList<>(meta.lore()) : new ArrayList<>();
        String levelText = switch (nextLevel) {
            case 1 -> "<#a0fc97>Nivel 1";
            case 2 -> "<#fffa61>Nivel 2";
            case 3 -> "<#fcc072>Nivel 3";
            case 4 -> "<#ff8161>Nivel 4";
            case 5 -> "<#ff4747>Nivel 5";
            default -> "<#a0fc97>Nivel ¿?";
        };

        if (!lore.isEmpty() && lore.getFirst().toString().contains("Poder de Vuelo")) {
            lore.set(0, Messenger.mini("<!italic><#9c1a1a>\uD83D\uDCA5</#9c1a1a> <#fc993d>Poder de Vuelo:</#fc993d> " + levelText));
        } else {
            lore.addFirst(Messenger.mini("<!italic><#9c1a1a>\uD83D\uDCA5</#9c1a1a> <#fc993d>Poder de Vuelo:</#fc993d> " + levelText));
        }

        meta.lore(lore);

        // Apply a short cooldown to prevent rapid usage
        Bukkit.getScheduler().runTaskLater(Panitacraft.getInstance(), () -> {
            player.setCooldown(item, 1 * 20);
        }, 1L);

        item.setItemMeta(meta);
        Messenger.prefixedSend(player, "&7El &bPoder de Vuelo &7del cohete cambiado a &b" + levelText + "&7.");
    }
}
