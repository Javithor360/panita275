package com.panita.panita275.milestone_events.model;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.milestone_events.util.EventProgressManager;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MilestoneEvent {
    private final String name;
    private final boolean active;
    private final EventType type;
    private final String craftingItem; // Exclusive to CRAFTING type
    private final long goal;
    private final Map<Integer, Milestone> milestones; // id -> Milestone
    private final String bossBarTitle;
    private final String bossBarFinalTitle;
    private final BossBar.Color bossBarColor;
    private final BossBar.Overlay bossBarStyle;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final boolean bossBarEnabled;

    // helpers
    private String currentTitle;
    private float currentProgress;

    public MilestoneEvent(String name, boolean active, LocalDateTime start, LocalDateTime end,
                          EventType type, String craftingItem, long goal,
                          Map<Integer, Milestone> milestones, String bossBarTitle,
                          String bossBarFinalTitle, BossBar.Color bossBarColor, BossBar.Overlay bossBarStyle, boolean bossBarEnabled) {
        this.name = name;
        this.active = active;
        this.start = start;
        this.end = end;
        this.type = type;
        this.craftingItem = craftingItem;
        this.goal = goal;
        this.milestones = milestones;
        this.bossBarTitle = bossBarTitle;
        this.bossBarFinalTitle = bossBarFinalTitle;
        this.bossBarColor = bossBarColor;
        this.bossBarStyle = bossBarStyle;
        this.bossBarEnabled = bossBarEnabled;
    }

    /**
     * Updates the boss bar progress and title based on current event progress.
     */
    public void updateBossBar() {
        if (!bossBarEnabled) return;
        handleBossBardUpdate();

        Panitacraft.getInstance().getServer().getOnlinePlayers().forEach(player -> {
            Messenger.showBossBar(player, name, currentTitle, bossBarColor, bossBarStyle, currentProgress);
        });
    }

    public void updateBossBar(Player player) {
        if (!bossBarEnabled) return;
        handleBossBardUpdate();

        Messenger.showBossBar(player, name, currentTitle, bossBarColor, bossBarStyle, currentProgress);
    }

    private void handleBossBardUpdate() {
        if (!isActive()) return;
        int current = EventProgressManager.getProgress(name);

        if (current >= goal) {
            if (bossBarFinalTitle != null && !bossBarFinalTitle.isBlank()) {
                currentTitle = applyPlaceholders(bossBarFinalTitle);
                currentProgress = 1.0f;
            }
            return;
        }

        // Update title and progress
        currentTitle = applyPlaceholders(bossBarTitle);
        currentProgress = (float) calculateProgressForBar(current);
    }

    /**
     * Replaces all placeholders in the given raw text.
     * Supported placeholders:
     * %current_progress%, %next_milestone%, %milestone_count%, %total_amount%, %timer%, %current_milestone%
     */
    private String applyPlaceholders(String raw) {
        int current = EventProgressManager.getProgress(name);

        // Next milestone amount required - %next_milestone%
        Milestone next = milestones.values().stream()
                .filter(m -> current < m.getAmount())
                .min(Comparator.comparingLong(Milestone::getAmount))
                .orElse(null);
        long nextAmount = next != null ? next.getAmount() : goal;

        // Current milestone - %current_milestone%
        int currentMilestone = milestones.entrySet().stream()
                .filter(e -> current >= e.getValue().getAmount())
                .map(Map.Entry::getKey)
                .max(Integer::compareTo)
                .orElse(0) + 1;

        // Time left - %timer%
        String timer = "Inactive Event";
        if (isActive()) {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(end)) timer = "Event Ended";
            else {
                long seconds = java.time.Duration.between(now, end).getSeconds();
                long days = seconds / 86400;
                long hours = (seconds % 86400) / 3600;
                long minutes = (seconds % 3600) / 60;
                long secs = seconds % 60;
                StringBuilder sb = new StringBuilder();
                if (days > 0) sb.append(days).append("d ");
                if (hours > 0 || days > 0) sb.append(hours).append("h ");
                if (minutes > 0 || hours > 0 || days > 0) sb.append(minutes).append("m ");
                sb.append(secs).append("s");
                timer = sb.toString().trim();
            }
        }

        // Replace placeholders
        return raw.replace("%current_progress%", String.valueOf(current))
                .replace("%next_milestone%", String.valueOf(nextAmount))
                .replace("%milestone_count%", String.valueOf(milestones.size()))
                .replace("%total_amount%", String.valueOf(goal))
                .replace("%timer%", timer)
                .replace("%current_milestone%", String.valueOf(currentMilestone));
    }

    /**
     * Calculates the progress for the boss bar, considering milestones.
     *
     * @param current the current progress amount
     * @return the progress ratio (0.0 to 1.0)
     */
    private double calculateProgressForBar(int current) {
        if (milestones.isEmpty()) return Math.min((double) current / goal, 1.0);
        List<Long> sortedMilestones = milestones.values().stream().map(Milestone::getAmount).sorted().toList();
        long prev = 0;
        long next = goal;
        for (long m : sortedMilestones) {
            if (current < m) {
                next = m;
                break;
            }
            prev = m;
        }
        return Math.min((double) (current - prev) / (next - prev), 1.0);
    }

    /**
     * Execute a command list configured for a specific milestone.
     *
     * @param milestone the milestone identifier
     */
    public void executeMilestoneCommands(Milestone milestone) {
        if (milestone == null) return;

        List<String> commands = milestone.getCommands();
        if (commands != null) {
            commands.forEach(cmd -> {
                if (!cmd.isBlank()) {
                    Panitacraft.getInstance().getServer().dispatchCommand(Panitacraft.getInstance().getServer().getConsoleSender(), cmd);
                }
            });
        }

        // Milestone message broadcast
        if (milestone.getMessage() != null && !milestone.getMessage().isBlank()) {
            Messenger.prefixedBroadcast(milestone.getMessage());
        }
    }

    /**
     * Hides the boss bar for a specific player.
     *
     * @param player the player to hide the boss bar from
     */
    public void hideBossBar(Player player) {
        Messenger.hideBossBar(player, name);
    }

    /**
     * Gets full information about the event, including current progress, milestones, and time remaining.
     *
     * @return an EventFullDetails object containing all relevant information
     */
    public EventFullDetails getFullInfo() {
        int currentProgress = EventProgressManager.getProgress(name);

        // Milestones as a string "25 -> 50 -> 100"
        String milestonesInfo = milestones.values().stream()
                .map(m -> String.valueOf(m.getAmount()))
                .sorted((a, b) -> Long.compare(Long.parseLong(a), Long.parseLong(b)))
                .collect(Collectors.joining(" -> "));

        // Progress to next milestone
        List<Long> sortedMilestones = milestones.values().stream()
                .map(Milestone::getAmount)
                .sorted()
                .toList();

        long prev = 0;
        long next = goal;

        for (long m : sortedMilestones) {
            if (currentProgress < m) {
                next = m;
                break;
            }
            prev = m;
        }

        float progressRatio = Math.min((float) (currentProgress - prev) / (next - prev), 1.0f);

        // Timer
        String timeRemaining;
        LocalDateTime now = LocalDateTime.now();
        if (!active) timeRemaining = "Inactive Event";
        else if (now.isAfter(end)) timeRemaining = "Event Ended";
        else {
            long seconds = java.time.Duration.between(now, end).getSeconds();
            long days = seconds / 86400;
            long hours = (seconds % 86400) / 3600;
            long minutes = (seconds % 3600) / 60;
            long secs = seconds % 60;
            StringBuilder sb = new StringBuilder();
            if (days > 0) sb.append(days).append("d ");
            if (hours > 0 || days > 0) sb.append(hours).append("h ");
            if (minutes > 0 || hours > 0 || days > 0) sb.append(minutes).append("m ");
            sb.append(secs).append("s");
            timeRemaining = sb.toString();
        }

        return new EventFullDetails(
                name,
                type,
                active,
                currentProgress,
                progressRatio,
                milestonesInfo,
                prev,
                next,
                goal,
                timeRemaining,
                start,
                end,
                type == EventType.CRAFTING ? craftingItem : null
        );
    }

    /**
     * Retrieves the top players based on their contribution to this event.
     *
     * @param limit the maximum number of top players to return
     * @return a map of player names to their contribution amounts, sorted in descending order
     */
    public Map<String, Integer> getTopPlayers(int limit) {
        Map<String, Integer> progressMap = EventProgressManager.getPlayerProgress(name);

        if (progressMap.isEmpty()) return Map.of();

        return progressMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    public Map<Integer, Milestone> getMilestones() {
        return milestones;
    }

    public EventType getType() {
        return type;
    }

    public String getCraftingItem() {
        return craftingItem;
    }

    public String getName() {
        return name;
    }

    public long getGoal() {
        return goal;
    }

    public boolean isActive() {
        return active;
    }
}
