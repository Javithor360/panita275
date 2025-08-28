package com.panita.panita275.milestone_events.util;

import com.panita.panita275.milestone_events.model.EventType;
import com.panita.panita275.milestone_events.model.Milestone;
import com.panita.panita275.milestone_events.model.MilestoneEvent;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MilestoneEventManager {
    private static final Map<String, MilestoneEvent> events = new HashMap<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void loadEvents(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "milestonesevents.yml");
        if (!file.exists()) {
            plugin.saveResource("milestonesevents.yml", false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getKeys(false)) {

            // Fechas
            LocalDateTime start = LocalDateTime.parse(config.getString(key + ".startDate"), formatter);
            LocalDateTime end = LocalDateTime.parse(config.getString(key + ".endDate"), formatter);
            LocalDateTime now = LocalDateTime.now();

            // Activo según config y fechas
            boolean active = config.getBoolean(key + ".active") && !now.isBefore(start) && !now.isAfter(end);

            EventType type = EventType.valueOf(config.getString(key + ".type"));
            String craftingItem = config.getString(key + ".craftingItem");
            long goal = config.getLong(key + ".goal");

            // Milestones
            Map<Integer, Milestone> milestones = new HashMap<>();
            if (config.contains(key + ".milestones")) {
                for (String milestoneKey : config.getConfigurationSection(key + ".milestones").getKeys(false)) {
                    long amount = config.getLong(key + ".milestones." + milestoneKey + ".amount");
                    String message = config.getString(key + ".milestones." + milestoneKey + ".message", "");
                    List<String> commands = config.getStringList(key + ".milestones." + milestoneKey + ".commandExecute");
                    milestones.put(Integer.parseInt(milestoneKey), new Milestone(amount, message, commands));
                }
            }

            // BossBar
            String bossBarTitle = config.getString(key + ".bossbar.title", "Evento: %current_progress% / %next_milestone%");
            String bossBarFinalTitle = config.getString(key + ".bossbar.finalGoalReachedTitle", "¡Evento Completado!");
            BossBar.Color color = BossBar.Color.valueOf(config.getString(key + ".bossbar.color", "GREEN"));
            BossBar.Overlay style = BossBar.Overlay.valueOf(config.getString(key + ".bossbar.style", "NOTCHED_6"));

            // Crear evento
            MilestoneEvent event = new MilestoneEvent(
                    key, active, start, end, type, craftingItem, goal, milestones,
                    bossBarTitle, bossBarFinalTitle, color, style
            );

            events.put(key, event);
        }
    }


    public static Optional<MilestoneEvent> getActiveEvent() {
        return events.values().stream().filter(MilestoneEvent::isActive).findFirst();
    }

    public static Map<String, MilestoneEvent> getEvents() {
        return Collections.unmodifiableMap(events);
    }
}
