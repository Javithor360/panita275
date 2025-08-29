package com.panita.panita275.core.commands.base;

import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.identifiers.CommandSpec;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandSpec(
        name = "panitacraft",
        description = "PanitaCraft's main command",
        syntax = "/panitacraft <subcommand>",
        aliases = {"pc", "panita"},
        permission = "panitacraft.command.panitacraft"
)
public class PanitacraftCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Bukkit.dispatchCommand(sender, "pc help");
    }
}
