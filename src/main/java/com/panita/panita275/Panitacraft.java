package com.panita.panita275;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.CommandRegistry;
import com.panita.panita275.core.config.Config;
import com.panita.panita275.core.config.ConfigManager;
import com.panita.panita275.core.listeners.ListenerRegistry;
import com.panita.panita275.core.modules.ModuleManager;
import com.panita.panita275.core.modules.PluginModule;
import com.panita.panita275.optimization.OptimizationModule;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Panitacraft extends JavaPlugin {
    private static Panitacraft instance;
    private ModuleManager moduleManager;
    private static ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Panitacraft is starting up!");

        instance = this;

        // Messenger setup
        BukkitAudiences adventure = BukkitAudiences.create(this);
        Messenger.init(adventure);

        // Config setup
        Config.load(this);
        configManager = new ConfigManager(this, Config.raw());

        new CommandRegistry(this).registerAll("com.panita.panita275.core.commands.base"); // load base commands first
        moduleManager = new ModuleManager(this);
        moduleManager.register(new OptimizationModule());
    }

    @Override
    public void onDisable() {
        if (moduleManager != null) moduleManager.disableAll();

        // Plugin shutdown logic
        getLogger().info("Panitacraft is shutting down!");
    }

    /**
     * Gets the ConfigManager instance.
     *
     * @return The ConfigManager instance.
     */
    public static ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * Gets the ModuleManager instance.
     *
     * @return The ModuleManager instance.
     */
    public static ModuleManager getModuleManager() {
        return instance.moduleManager;
    }

    /**
     * Sets the ConfigManager instance.
     *
     * @param manager The ConfigManager instance to set.
     */
    public static void setConfigManager(ConfigManager manager) {
        configManager = manager;
    }

    /**
     * Gets the singleton instance of the Panitacraft plugin.
     *
     * @return The Panitacraft instance.
     */
    public static Panitacraft getInstance() {
        return instance;
    }
}
