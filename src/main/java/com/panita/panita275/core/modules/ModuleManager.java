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
    private final ConfigManager configManager;
    private final CommandRegistry cmdRegistry;
    private final ListenerRegistry listenerRegistry;

    private final List<PluginModule> active = new ArrayList<>();
    private final List<PluginModule> allModules = new ArrayList<>();

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
     * Retrieves a registered module by its ID.
     *
     * @param id The ID of the module.
     * @return The PluginModule instance, or null if not found.
     */
    public PluginModule getModule(String id) {
        return active.stream()
                .filter(m -> m.id().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if a module with the given ID is currently active.
     *
     * @param id The ID of the module.
     * @return True if the module is active, false otherwise.
     */
    public boolean isModuleActive(String id) {
        return getModule(id) != null;
    }

    /**
     * Returns the list of all registered modules, including inactive ones.
     */
    public List<PluginModule> getAllModules() {
        return List.copyOf(allModules);
    }

    /**
     * Returns a copy of the list of active modules.
     */
    public List<PluginModule> getActiveModules() {
        return new ArrayList<>(active);
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
        allModules.add(m);

        // Check if the module is enabled in the config
        boolean enabled = configManager.getBoolean(m.id() + ".enabled", true);
        m.setEnabled(enabled);

        if (!enabled) {
            plugin.getLogger().info("[Module] " + m.id() + " -> disabled in config.yml");
            return;
        }

        enableModule(m);
    }

    private void enableModule(PluginModule module) {
        // Enable the module
        plugin.getLogger().info("[Module] Enabling " + module.id());

        // Register commands and listeners
        cmdRegistry.registerAll(module.commandPackage());
        listenerRegistry.registerAll(module.listenerPackage());
        module.onEnable(plugin);

        // Add to active modules list
        active.add(module);
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

    /**
     * Registers commands and listeners for a given module.
     * If the module is not already active, it will be added to the active list.
     *
     * @param module The module whose commands and listeners are to be registered.
     */
    public void registerCommandsAndListeners(PluginModule module) {
        cmdRegistry.registerAll(module.commandPackage());
        listenerRegistry.registerAll(module.listenerPackage());
        if (!active.contains(module)) active.add(module);
    }
}
