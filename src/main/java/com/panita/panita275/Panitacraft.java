package com.panita.panita275;

import com.panita.panita275.core.commands.CommandRegistry;
import com.panita.panita275.core.listeners.ListenerRegistry;
import com.panita.panita275.core.modules.PluginModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Panitacraft extends JavaPlugin {
    private final List<PluginModule> modules = List.of();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Panitacraft is starting up!");

        // Register commands
        CommandRegistry cmdRegistry = new CommandRegistry(this);
        ListenerRegistry listenerRegistry = new ListenerRegistry(this, getConfig());

        for (PluginModule module : modules) {
            getLogger().info("[Panitacraft Core] Enabling module: " + module.name());

            // Register commands and listeners for the module
            cmdRegistry.registerAll(module.commandPackage());
            listenerRegistry.registerAll(module.listenerPackage());

            // Additional module-specific initialization can go here
            module.onEnable(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Panitacraft is shutting down!");

        for (PluginModule module : modules) {
            getLogger().info("[Panitacraft Core] Disabling module: " + module.name());
            module.onDisable(this);
        }
    }
}
