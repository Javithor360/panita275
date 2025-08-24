package com.panita.panita275.optimization.commands;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.dynamic.TabSuggestingCommand;
import com.panita.panita275.core.commands.identifiers.CommandMeta;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.optimization.OptimizationModule;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SubCommandSpec(
        parent = "panitacraft optimization",
        name = "toggle",
        description = "Toggle optimization features on or off",
        syntax = "/panitacraft optimization toggle <on|off>"
)
public class ToggleCommand implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        OptimizationModule module = (OptimizationModule) Panitacraft.getModuleManager().getModule("optimization");

        if (module == null) {
            Messenger.prefixedSend(sender, "<red>El módulo de optimizaciones no está disponible.</red>");
            return;
        }

        boolean current = module.isEnabled();
        boolean value;

        if (args.length == 0) {
            // If no argument is provided, toggle the current value
            value = !current;
        } else {
            String inputValue = args[0].toLowerCase();
            if (!inputValue.equals("on") && !inputValue.equals("off")) {
                Messenger.prefixedSend(sender, "<red>Debes ingresar un valor válido: <yellow>on</yellow>/<yellow>off</yellow></red>");
                return;
            }
            value = inputValue.equals("on");
        }

        // Update the configuration and apply the new value
        Panitacraft.getConfigManager().updateBoolean("optimization.enabled", value, null);
        module.setEnabled(value);

        Messenger.prefixedSend(sender,
                "<green>El módulo de optimizaciones ha sido " + (value ? "activado" : "desactivado") + ".</green>");
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Stream.of("on", "off")
                    .filter(s -> s.startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
