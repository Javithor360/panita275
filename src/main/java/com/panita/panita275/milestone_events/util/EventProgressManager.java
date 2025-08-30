package com.panita.panita275.milestone_events.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.panita.panita275.milestone_events.model.MilestoneEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventProgressManager {
    private static JsonObject progressData = new JsonObject();
    private static File file;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void init(JavaPlugin plugin) {
        try {
            file = new File(plugin.getDataFolder(), "eventprogress.json");
            if (!file.exists()) {
                file.createNewFile();
                progressData = new JsonObject();
                save();
            } else {
                progressData = gson.fromJson(new FileReader(file), JsonObject.class);
                if (progressData == null) progressData = new JsonObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(progressData, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds progress to an event based on a specific player.
     * @param eventName the event identifier
     * @param playerName the player's name
     * @param amount the amount of progress to add
     */
    public static void addProgress(String eventName, String playerName, int amount, MilestoneEvent event) {
        // global progress
        JsonObject eventProgress = progressData.has(eventName)
                ? progressData.getAsJsonObject(eventName)
                : new JsonObject();
        int current = eventProgress.has("current") ? eventProgress.get("current").getAsInt() : 0;
        current += amount;
        eventProgress.addProperty("current", current);

        // player-specific progress
        JsonObject players = eventProgress.has("players")
                ? eventProgress.getAsJsonObject("players")
                : new JsonObject();
        int playerCurrent = players.has(playerName) ? players.get(playerName).getAsInt() : 0;
        players.addProperty(playerName, playerCurrent + amount);
        eventProgress.add("players", players);

        // Reached milestones
        JsonObject milestonesReached = eventProgress.has("milestonesReached")
                ? eventProgress.getAsJsonObject("milestonesReached")
                : new JsonObject();

        // Check and update milestones
        int finalCurrent = current;
        event.getMilestones().forEach((id, milestone) -> {
            if (finalCurrent >= milestone.getAmount() && !milestonesReached.has(String.valueOf(id))) {
                // mark milestone as reached
                milestonesReached.addProperty(String.valueOf(id), LocalDateTime.now().toString());
                event.executeMilestoneCommands(milestone);
            }
        });

        eventProgress.add("milestonesReached", milestonesReached);
        progressData.add(eventName, eventProgress);
        save();
        event.updateBossBar();
    }

    /**
     * Retrieves the total progress for a specific event.
     * @param eventName the event identifier
     * @return the current progress amount
     */
    public static int getProgress(String eventName) {
        if (!progressData.has(eventName)) return 0;
        JsonObject eventProgress = progressData.getAsJsonObject(eventName);
        return eventProgress.has("current") ? eventProgress.get("current").getAsInt() : 0;
    }

    /**
     * Gets the progress of all players for a specific event.
     * @param eventName the event identifier
     * @return a map of player names to their progress amounts
     */
    public static Map<String, Integer> getPlayerProgress(String eventName) {
        if (!progressData.has(eventName)) return Collections.emptyMap();
        JsonObject eventProgress = progressData.getAsJsonObject(eventName);
        Map<String, Integer> map = new HashMap<>();
        if (eventProgress.has("players")) {
            JsonObject players = eventProgress.getAsJsonObject("players");
            for (Map.Entry<String, JsonElement> entry : players.entrySet()) {
                map.put(entry.getKey(), entry.getValue().getAsInt());
            }
        }
        return map;
    }

    /**
     * Marks a milestone as reached for a specific event.
     * @param eventName the event identifier
     * @param milestoneId the milestone identifier
     */
    public static void markMilestoneReached(String eventName, int milestoneId) {
        JsonObject eventProgress = progressData.has(eventName)
                ? progressData.getAsJsonObject(eventName)
                : new JsonObject();

        JsonObject milestonesReached = eventProgress.has("milestonesReached")
                ? eventProgress.getAsJsonObject("milestonesReached")
                : new JsonObject();

        milestonesReached.addProperty(String.valueOf(milestoneId), LocalDateTime.now().toString());
        eventProgress.add("milestonesReached", milestonesReached);

        progressData.add(eventName, eventProgress);
        save();
    }

    /**
     * Retrieves the milestones that have been reached for a specific event.
     * @param eventName the event identifier
     * @return a map of milestone IDs to the timestamps when they were reached
     */
    public static Map<Integer, String> getMilestonesReached(String eventName) {
        Map<Integer, String> map = new HashMap<>();
        if (!progressData.has(eventName)) return map;
        JsonObject eventProgress = progressData.getAsJsonObject(eventName);
        if (eventProgress.has("milestonesReached")) {
            JsonObject milestones = eventProgress.getAsJsonObject("milestonesReached");
            for (Map.Entry<String, JsonElement> entry : milestones.entrySet()) {
                map.put(Integer.parseInt(entry.getKey()), entry.getValue().getAsString());
            }
        }
        return map;
    }
}
