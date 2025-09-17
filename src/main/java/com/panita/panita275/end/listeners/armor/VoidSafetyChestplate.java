package com.panita.panita275.end.listeners.armor;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.core.util.SoundUtils;
import com.panita.panita275.end.util.ArmorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VoidSafetyChestplate implements Listener {
    @EventHandler
    public void onVoidDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.VOID) return;

        if (!ArmorUtils.isDragonSlayerChest(player.getInventory().getChestplate())) return;
        if (!ArmorUtils.hasFullDragonSlayerSet(player)) return;

        event.setCancelled(true);

        Location target;
        World world = player.getWorld();

        if (world.getEnvironment() == World.Environment.NETHER) {
            // Nether -> coords * 8 to overworld
            World overworld = Bukkit.getWorlds().stream()
                .filter(w -> w.getEnvironment() == World.Environment.NORMAL)
                .findFirst()
                .orElse(null);
            if (overworld == null) return;

            double x = player.getLocation().getX() * 8;
            double z = player.getLocation().getZ() * 8;
            target = new Location(overworld, x, 360, z);
        } else if (world.getEnvironment() == World.Environment.THE_END) {
            // End -> same coords but in overworld
            World overworld = Bukkit.getWorlds().stream()
                .filter(w -> w.getEnvironment() == World.Environment.NORMAL)
                .findFirst()
                .orElse(null);
            if (overworld == null) return;

            double x = player.getLocation().getX();
            double z = player.getLocation().getZ();
            target = new Location(overworld, x, 360, z);
        } else {
            // Overworld / Custom Dimension -> fixed coords
            target = new Location(world, 0, 360, 0);
        }

        String soundKey = Panitacraft.getConfigManager()
                .getString("quality-of-life.totems.soundName", ConfigDefaults.QOL_TOTEMS_SOUNDNAME);

        player.teleport(target);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 180 * 20, 0, false, true, true));
        SoundUtils.playGlobal(soundKey, 1.0f, 1.0f);
        Messenger.prefixedPlaceholderBroadcast(player, "&b%player_name% &7estuvo a punto de perderse en el vacío ha sido salvado por el poder de los Cazadragones.");
    }
}
