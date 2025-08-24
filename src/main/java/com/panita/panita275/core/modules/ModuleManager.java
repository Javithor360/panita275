package com.panita.panita275.core.modules;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.commands.CommandRegistry;
import com.panita.panita275.core.config.ConfigManager;
import com.panita.panita275.core.listeners.ListenerRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manages the registration and lifecycle of plugin modules.
 */
public class ModuleManager {
    private final JavaPlugin plugin;
    private final CommandRegistry cmdRegistry;
    private final ListenerRegistry listenerRegistry;
    private final List<PluginModule> active = new ArrayList<>();
    private final ConfigManager configManager;

    /**
     * Constructs a ModuleManager for the given plugin.
     *
     * @param plugin The main JavaPlugin instance.
     */
    public ModuleManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.cmdRegistry = new CommandRegistry(plugin);
        this.listenerRegistry = new ListenerRegistry(plugin, plugin.getConfig());
        this.configManager = Panitacraft.getConfigManager();
    }

    /**
     * Registers multiple plugin modules.
     *
     * @param modules The modules to register.
     */
    public void register(PluginModule... modules) {
        Arrays.stream(modules).forEach(this::registerOne);
    }

    /**
     * Registers a single plugin module.
     *
     * @param m The module to register.
     */
    private void registerOne(PluginModule m) {
        // Check if the module is enabled in the config
        boolean enabled = configManager.getBoolean(m.id() + ".enabled", true);
        if (!enabled) {
            plugin.getLogger().info("[Module] " + m.id() + " -> disabled in config.yml");
            return;
        }

        // Enable the module
        plugin.getLogger().info("[Module] Enabling " + m.id());

        // Register commands and listeners
        cmdRegistry.registerAll(m.commandPackage());
        listenerRegistry.registerAll(m.listenerPackage());
        m.onEnable(plugin);

        // Add to active modules list
        active.add(m);
    }

    /**
     * Disables all registered plugin modules.
     */
    public void disableAll() {
        for (PluginModule m : active) {
            try { m.onDisable(plugin); }
            catch (Exception ignored) {}
        }
        active.clear();
    }
}
