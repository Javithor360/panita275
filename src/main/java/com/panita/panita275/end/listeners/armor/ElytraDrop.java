package com.panita.panita275.end.listeners.armor;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ElytraDrop implements Listener {
    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (!Panitacraft.getConfigManager().getBoolean("end.event.armor.dragonDropElytra", ConfigDefaults.END_EVENT_ARMOR_DRAGON_DROP_ELYTRA)) return;

        if (!(event.getEntity() instanceof EnderDragon)) return;
        ItemStack elytra = CustomItemManager.getItem("dragonslayer_elytra");
        if (elytra == null) return;
        event.getDrops().add(elytra);
        event.setDroppedExp(2500);
    }
}
