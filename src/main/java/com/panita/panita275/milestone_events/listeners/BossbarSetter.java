package com.panita.panita275.milestone_events.listeners;

import com.panita.panita275.milestone_events.model.MilestoneEvent;
import com.panita.panita275.milestone_events.util.MilestoneEventManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BossbarSetter implements Listener {
    // Add players to the boss bar when they join
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MilestoneEventManager.getActiveEvent().ifPresent(e -> {
            e.updateBossBar(event.getPlayer());
        });
    }

    // Remove players from the boss bar when they leave
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        MilestoneEventManager.getActiveEvent().ifPresent(e -> e.hideBossBar(event.getPlayer()));
    }
}
