package com.panita.panita275.milestone_events.commands.milestones;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.dynamic.TabSuggestingCommand;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.core.util.CommandUtils;
import com.panita.panita275.milestone_events.model.MilestoneEvent;
import com.panita.panita275.milestone_events.util.MilestoneEventManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;

@SubCommandSpec(
        parent = "panitacraft milestones",
        name = "top",
        description = "View top contributing players for a milestone event",
        syntax = "/pc milestones top <event_name> [player_name]",
        playerOnly = false
)
public class MilestonesTop implements AdvancedCommand, TabSuggestingCommand {
    private static final int TOP_LIMIT = 10;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        String eventName = args[0];
        Optional<MilestoneEvent> optEvent = MilestoneEventManager.getEvents().values().stream()
                .filter(e -> e.getName().equalsIgnoreCase(eventName))
                .findFirst();

        if (optEvent.isEmpty()) {
            Messenger.prefixedSend(sender, "&cNo milestone event found with name &e" + eventName);
            return;
        }

        MilestoneEvent event = optEvent.get();
        Map<String, Integer> topPlayers = event.getTopPlayers(TOP_LIMIT);

        if (topPlayers.isEmpty()) {
            Messenger.prefixedSend(sender, "&cNo players have contributed to this event yet.");
            return;
        }

        if (args.length >= 2) {
            String playerName = args[1];
            int position = 1;
            boolean found = false;

            for (Map.Entry<String, Integer> entry : topPlayers.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(playerName)) {
                    Messenger.prefixedSend(sender, "&aPlayer &e" + playerName + " &ais ranked &e#" + position + " &awith &f" + entry.getValue() + " points");
                    found = true;
                    break;
                }
                position++;
            }

            if (!found) {
                Messenger.prefixedSend(sender, "&cPlayer &e" + playerName + " &cis not in the top " + TOP_LIMIT);
            }

        } else {
            Messenger.prefixedSend(sender, "&aTop " + TOP_LIMIT + " contributors for event &e" + eventName + ":");
            int rank = 1;
            for (Map.Entry<String, Integer> entry : topPlayers.entrySet()) {
                Messenger.prefixedSend(sender, "&e" + rank + ". &a" + entry.getKey() + " &7- &f" + entry.getValue() + " points");
                rank++;
            }
        }
    }

    @Override
    public void applySuggestions(com.panita.panita275.core.commands.identifiers.CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> MilestoneEventManager.getEvents().values().stream()
                .map(MilestoneEvent::getName)
                .filter(name -> name.toLowerCase().startsWith(context.getCurrentArg().toLowerCase()))
                .toList());

        meta.setArgumentSuggestion(1, context -> Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(context.getCurrentArg().toLowerCase()))
                .toList());
    }
}
