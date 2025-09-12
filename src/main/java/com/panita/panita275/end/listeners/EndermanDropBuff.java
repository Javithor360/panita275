package com.panita.panita275.end.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class EndermanDropBuff implements Listener {
    @EventHandler
    public void onEndermanDeath(EntityDeathEvent event) {
        // Check if the entity that died is an Enderman
        if (!(event.getEntity() instanceof Enderman enderman)) return;

        // Check if the feature is enabled in the config
        double multiplier = Panitacraft.getConfigManager().getDouble(
                "end.endermanPearlDropBuff",
                ConfigDefaults.END_ENDER_PEARL_DROP_BUFF
        );

        if (multiplier == 1.0) return; // No change needed if multiplier is 1.0

        // Iterate through the drops to find Ender Pearls and count them
        int pearlCount = 0;
        Iterator<ItemStack> it = event.getDrops().iterator();

        while (it.hasNext()) {
            ItemStack drop = it.next();
            if (drop.getType() == Material.ENDER_PEARL) {
                pearlCount += drop.getAmount();
                it.remove(); // Remove the original Ender Pearls from the drops
            }
        }

        // If there were any Ender Pearls, add the buffed amount back to the drops
        if (pearlCount > 0) {
            int newAmount = (int) Math.floor(pearlCount * multiplier);

            if (newAmount > 0) {
                event.getDrops().add(new ItemStack(Material.ENDER_PEARL, newAmount));
                event.setDroppedExp(135);
            }
        }
    }
}
