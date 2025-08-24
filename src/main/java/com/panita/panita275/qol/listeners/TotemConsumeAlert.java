package com.panita.panita275.qol.listeners;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.config.ConfigDefaults;
import com.panita.panita275.core.util.SoundUtils;
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
        if (event.isCancelled()) return;

        if (!(event.getEntity() instanceof Player player)) return;

        if (!(Panitacraft.getConfigManager()
                .getBoolean("quality-of-life.totems.alert", ConfigDefaults.QOL_TOTEMS_ALERT))) return;

        Messenger.prefixedPlaceholderBroadcast(player, Panitacraft.getConfigManager()
                .getString("quality-of-life.totems.message", ConfigDefaults.QOL_TOTEMS_MESSAGE));

        if (!Panitacraft.getConfigManager()
                .getBoolean("quality-of-life.totems.playSound", ConfigDefaults.QOL_TOTEMS_PLAYSOUND)) return;

        String soundKey = Panitacraft.getConfigManager()
                .getString("quality-of-life.totems.soundName", ConfigDefaults.QOL_TOTEMS_SOUNDNAME);
        SoundUtils.playGlobal(soundKey, 1.0f, 1.0f);
    }
}
