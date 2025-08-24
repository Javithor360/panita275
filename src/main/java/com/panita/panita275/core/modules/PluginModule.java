package com.panita.panita275.core.modules;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public interface PluginModule {
    /** The name of the module */
    String id();

    /** The base package where the module's classes are located */
    String basePackage();

    /** The package where command classes are located */
    default String commandPackage() { return basePackage() + ".commands"; }

    /** The package where event listener classes are located */
    default String listenerPackage() { return basePackage() + ".listeners"; }

    /** Access to the configuration manager */
    default ConfigManager configManager() {
        return Panitacraft.getConfigManager();
    }

    /** Called when the plugin is enabled */
    default void onEnable(JavaPlugin plugin) {}

    /** Called when the plugin is disabled */
    default void onDisable(JavaPlugin plugin) {}

    /** Whether the module is enabled */
    boolean isEnabled();

    /** Set whether the module is enabled */
    void setEnabled(boolean value);
}
