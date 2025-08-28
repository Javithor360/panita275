package com.panita.panita275.milestone_events;

import com.panita.panita275.core.modules.PluginModule;
import com.panita.panita275.milestone_events.util.EventProgressManager;
import com.panita.panita275.milestone_events.util.MilestoneEventManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MilestoneEventsModule implements PluginModule {
    private boolean enabled;
    public static String packageName = "com.panita.panita275.milestone_events";

    @Override
    public String id() {
        return "milestone_events";
    }

    @Override
    public String basePackage() {
        return packageName;
    }

    @Override
    public void onEnable(JavaPlugin plugin) {
        MilestoneEventManager.loadEvents(plugin);
        EventProgressManager.init(plugin);

        // If there's an active event, add all online players to the boss bar
        MilestoneEventManager.getActiveEvent().ifPresent(event -> {
            event.getBossBar().removeAll();
            plugin.getServer().getOnlinePlayers().forEach(p -> event.getBossBar().addPlayer(p));
        });
    }

    @Override
    public void onDisable(JavaPlugin plugin) {
        MilestoneEventManager.getEvents().values().forEach(evento -> {
            evento.getBossBar().setVisible(false);
            evento.getBossBar().removeAll();
        });
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean value) {
        enabled = value;
    }
}
