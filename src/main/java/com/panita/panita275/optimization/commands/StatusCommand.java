package com.panita.panita275.optimization.commands;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.optimization.OptimizationModule;
import org.bukkit.command.CommandSender;

@SubCommandSpec(
        parent = "panitacraft optimization",
        name = "status",
        description = "Check the current status of the optimization module.",
        syntax = "/panitacraft optimization status"
)
public class StatusCommand implements AdvancedCommand {
    private final OptimizationModule optimizationModule;

    public StatusCommand(OptimizationModule module) {
        this.optimizationModule = module;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        boolean isEnabled = optimizationModule.isEnabled();
        Messenger.prefixedSend(sender, "&7El modulo de optimización actualmente se encuentra " + (isEnabled ? "&aactivado" : "&cdesactivado"));
    }
}
