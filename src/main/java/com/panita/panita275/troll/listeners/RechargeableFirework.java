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
}
