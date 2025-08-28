package com.panita.panita275.milestone_events.model;

import java.util.List;

public class Milestone {
    private final long amount;
    private final String message;
    private final List<String> commands;

    public Milestone(long amount, String message, List<String> commands) {
        this.amount = amount;
        this.message = message;
        this.commands = commands;
    }

    public long getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getCommands() {
        return commands;
    }
}
