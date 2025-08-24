package com.panita.panita275.qol.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class TotemConsumeAlert implements Listener {
    @EventHandler
    public void onTotemConsume(EntityResurrectEvent event) {
        if (!event.isCancelled()) {
            if (event.getEntity() instanceof Player player) {
                if (Panitacraft.getConfigManager().getBoolean("quality-of-life.totems.alert", true)) {
                    String resurrectMessage = Panitacraft.getConfigManager().getString("quality-of-life.totems.message", "&b%player_name% &7Ha consumido un tótem en &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)");
                    Messenger.prefixedBroadcast(PlaceholderAPI.setPlaceholders(player, resurrectMessage));

                    if (Panitacraft.getConfigManager().getBoolean("quality-of-life.totems.playSound", false)) {
                        String soundName = Panitacraft.getConfigManager().getString("quality-of-life.totems.soundName", "ENTITY_VINDICATOR_HURT");
                        Sound sound = Sound.valueOf(soundName.toUpperCase());

                        for (Player p : player.getWorld().getPlayers()) {
                            p.playSound(p.getLocation(), sound, 2.0f, 2.0f);
                        }
                    }
                }
            }
        }
    }
}
