package com.panita.panita275.core.commands.base;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.core.config.Config;
import com.panita.panita275.core.config.ConfigManager;
import com.panita.panita275.core.modules.ModuleManager;
import com.panita.panita275.core.modules.PluginModule;
import org.bukkit.command.CommandSender;

@SubCommandSpec(
        parent = "panitacraft",
        name = "reload",
        description = "Reload PanitaCraft's configuration and all active modules.",
        syntax = "/panitacraft reload"
)
public class ReloadCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Panitacraft plugin = Panitacraft.getInstance();

        // Reload main config
        Config.reload(plugin);

        // Reload ConfigManager
        Panitacraft.setConfigManager(new ConfigManager(plugin, plugin.getConfig()));

        // Iterate through all modules and reload their configs
        ModuleManager moduleManager = Panitacraft.getModuleManager();
        for (PluginModule module : moduleManager.getAllModules()) {
            boolean enabledInConfig = Panitacraft.getConfigManager().getBoolean(module.id() + ".enabled", true);

            if (enabledInConfig && !module.isEnabled()) {
                // Enable module that was disabled
                module.setEnabled(true);
                module.onEnable(plugin);
                // Register commands and listeners for the module
                moduleManager.registerCommandsAndListeners(module);
            } else if (!enabledInConfig && module.isEnabled()) {
                // Disable module that was enabled
                module.onDisable(plugin);
                module.setEnabled(false);
            } else if (enabledInConfig) {
                // If module was already enabled, just reload it
                module.reload(plugin);
            }
        }

        Messenger.prefixedSend(sender, "&aConfiguración recargada correctamente.");
    }
}
