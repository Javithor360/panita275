package com.panita.panita275.core.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BiConsumer;

public class ConfigManager {
    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public ConfigManager(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
    }

    /**
     * Updates a boolean value in the config and callbacks for live update if provided.
     *
     * @param path config path
     * @param value new value
     * @param liveUpdate optional callback for immediate changes
     */
    public void updateBoolean(String path, boolean value, BiConsumer<String, Boolean> liveUpdate) {
        config.set(path, value);
        plugin.saveConfig();

        if (liveUpdate != null) {
            liveUpdate.accept(path, value);
        }
    }

    /**
     * Updates a string value in the config and callbacks for live update if provided.
     *
     * @param path config path
     * @param value new value
     * @param liveUpdate optional callback for immediate changes
     */
    public void updateString(String path, String value, BiConsumer<String, String> liveUpdate) {
        config.set(path, value);
        plugin.saveConfig();

        if (liveUpdate != null) {
            liveUpdate.accept(path, value);
        }
    }
}
