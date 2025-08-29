package com.panita.panita275.milestone_events.model;

import java.time.LocalDateTime;

public class EventFullDetails {
    public final String name;
    public final EventType type;
    public final boolean active;
    public final int currentProgress;
    public final float progressRatio;
    public final String milestonesInfo; // "25 -> 50 -> 100"
    public final long prevMilestoneAmount;
    public final long nextMilestoneAmount;
    public final long goal;
    public final String timeRemaining;
    public final LocalDateTime startDate;
    public final LocalDateTime endDate;
    public final String craftingItem; // solo si tipo CRAFTING

    public EventFullDetails(String name, EventType type, boolean active, int currentProgress,
                         float progressRatio, String milestonesInfo, long prevMilestoneAmount,
                         long nextMilestoneAmount, long goal, String timeRemaining,
                         LocalDateTime startDate, LocalDateTime endDate, String craftingItem) {
        this.name = name;
        this.type = type;
        this.active = active;
        this.currentProgress = currentProgress;
        this.progressRatio = progressRatio;
        this.milestonesInfo = milestonesInfo;
        this.prevMilestoneAmount = prevMilestoneAmount;
        this.nextMilestoneAmount = nextMilestoneAmount;
        this.goal = goal;
        this.timeRemaining = timeRemaining;
        this.startDate = startDate;
        this.endDate = endDate;
        this.craftingItem = craftingItem;
    }
}