package com.panita.panita275.core.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ConfigSection {
    private final String path;
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public ConfigSection(String path) {
        this.path = path == null ? "" : path.trim();
    }

    private String full(String key) {
        return (path.isEmpty() || key.isEmpty()) ? (path + key) : (path + "." + key);
    }

    public boolean getBoolean(String key, boolean def) {
        return Config.raw().getBoolean(full(key), def);
    }

    public int getInt(String key, int def) {
        return Config.raw().getInt(full(key), def);
    }

    public String getString(String key, String def) {
        return Config.raw().getString(full(key), def);
    }

    public List<String> getStringList(String key) {
        return Config.raw().getStringList(full(key));
    }

    public Component getMini(String key, String defRaw) {
        String raw = Config.raw().getString(full(key), defRaw);
        return mm.deserialize(raw != null ? raw : defRaw);
    }

    public ConfigurationSection getSection(String key) {
        return Config.raw().getConfigurationSection(full(key));
    }
}
