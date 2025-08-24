package com.panita.panita275.core.modules;

import org.bukkit.plugin.java.JavaPlugin;

public interface PluginModule {
    /** The name of the module */
    String name();

    /** The base package where the module's classes are located */
    String basePackage();

    /** Called when the plugin is enabled */
    default void onEnable(JavaPlugin plugin) {}

    /** Called when the plugin is disabled */
    default void onDisable(JavaPlugin plugin) {}

    /** The package where command classes are located */
    default String commandPackage() {
        return basePackage() + ".commands";
    }

    /** The package where event listener classes are located */
    default String listenerPackage() {
        return basePackage() + ".listeners";
    }
}
