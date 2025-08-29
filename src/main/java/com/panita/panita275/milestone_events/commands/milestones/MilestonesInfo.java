package com.panita.panita275.milestone_events.commands.milestones;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.dynamic.TabSuggestingCommand;
import com.panita.panita275.core.commands.identifiers.CommandMeta;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.milestone_events.model.EventFullDetails;
import com.panita.panita275.milestone_events.model.EventType;
import com.panita.panita275.milestone_events.model.MilestoneEvent;
import com.panita.panita275.milestone_events.util.MilestoneEventManager;
import org.bukkit.command.CommandSender;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

@SubCommandSpec(
        parent = "panitacraft milestones",
        name = "info",
        description = "View information about milestone events.",
        syntax = "/pc milestones info [event_name]",
        permission = "panitacraft.command.milestones.info",
        playerOnly = false
)
public class MilestonesInfo implements AdvancedCommand, TabSuggestingCommand {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            String eventName = args[0];
            Optional<MilestoneEvent> optEvent = MilestoneEventManager.getEvents().values().stream()
                    .filter(e -> e.getName().equalsIgnoreCase(eventName))
                    .findFirst();

            if (optEvent.isEmpty()) {
                Messenger.prefixedSend(sender, "&cNo milestone event found with name &e" + eventName);
                return;
            }

            EventFullDetails info = optEvent.get().getFullInfo();

            Messenger.prefixedSend(sender, "&aMilestone Event Info: &e" + info.name);
            Messenger.prefixedSend(sender, " &7Type: &e" + info.type);

            if (info.type == EventType.CRAFTING) {
                Messenger.prefixedSend(sender, "  &8Item to craft: &e" + info.craftingItem);
            }

            Messenger.prefixedSend(sender, " &7Active: &e" + info.active);
            Messenger.prefixedSend(sender, " &7Goal: &e" + info.goal);
            Messenger.prefixedSend(sender, "  &8Current Progress: &e" + info.currentProgress + " (" + String.format("%.2f", info.progressRatio * 100) + "%)");
            Messenger.prefixedSend(sender, "  &8Milestones: &e" + info.milestonesInfo);
            Messenger.prefixedSend(sender, "  &8Previous Milestone: &e" + info.prevMilestoneAmount);
            Messenger.prefixedSend(sender, "  &8Next Milestone: &e" + info.nextMilestoneAmount);
            Messenger.prefixedSend(sender, " &7Start: &e" + info.startDate.format(formatter));
            Messenger.prefixedSend(sender, " &7End: &e" + info.endDate.format(formatter));
            Messenger.prefixedSend(sender, " &7Time Remaining: &e" + info.timeRemaining);

        } else {
            Set<String> activeEvents = MilestoneEventManager.getEvents().values().stream()
                    .filter(MilestoneEvent::isActive)
                    .map(MilestoneEvent::getName)
                    .collect(java.util.stream.Collectors.toSet());

            if (activeEvents.isEmpty()) {
                Messenger.prefixedSend(sender, "&cNo active milestone events.");
                return;
            }

            Messenger.prefixedSend(sender, "&aActive Milestone Events:");
            Messenger.prefixedSend(sender, "&e" + String.join(", ", activeEvents));
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> MilestoneEventManager.getEvents().values().stream()
                .map(MilestoneEvent::getName)
                .filter(name -> name.toLowerCase().startsWith(context.getCurrentArg().toLowerCase()))
                .toList());
    }
}
