package com.panita.panita275.milestone_events.model;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.milestone_events.util.EventProgressManager;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MilestoneEvent {
    private final String name;
    private final boolean active;
    private final EventType type;
    private final String craftingItem; // Exclusive to CRAFTING type
    private final long goal;
    private final Map<Integer, Milestone> milestones; // id -> Milestone
    private final BossBar bossBar;
    private final String bossBarTitle;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public MilestoneEvent(String name, boolean active, LocalDateTime start, LocalDateTime end,
                          EventType type, String craftingItem, long goal,
                          Map<Integer, Milestone> milestones,
                          String bossBarTitle, BarColor color, BarStyle style) {
        this.name = name;
        this.active = active;
        this.start = start;
        this.end = end;
        this.type = type;
        this.craftingItem = craftingItem;
        this.goal = goal;
        this.milestones = milestones;
        this.bossBarTitle = bossBarTitle;
        this.bossBar = Panitacraft.getInstance().getServer().createBossBar(bossBarTitle, color, style);
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

    /** Updates the boss bar progress and title based on current event progress. */
    public void updateBossBar() {
        if (!isActive()) return;

        int current = EventProgressManager.getProgress(name);

        // Nearest milestone
        Milestone next = milestones.values().stream()
                .filter(m -> current < m.getAmount())
                .min(Comparator.comparingLong(Milestone::getAmount))
                .orElse(null);
        long nextAmount = next != null ? next.getAmount() : goal;

        String title = bossBarTitle
                .replace("%current_progress%", String.valueOf(current))
                .replace("%next_milestone%", String.valueOf(nextAmount))
                .replace("%milestone_count%", String.valueOf(milestones.size()))
                .replace("%total_amount%", String.valueOf(goal));

        bossBar.setTitle(title);
        bossBar.setProgress(Math.min((double) current / goal, 1.0));
    }

    /**
     * Execute a command list configured for a specific milestone.
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

    public BossBar getBossBar() {
        return bossBar;
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
