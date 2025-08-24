package com.panita.panita275;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.CommandRegistry;
import com.panita.panita275.core.config.Config;
import com.panita.panita275.core.listeners.ListenerRegistry;
import com.panita.panita275.core.modules.ModuleManager;
import com.panita.panita275.core.modules.PluginModule;
import com.panita.panita275.optimization.OptimizationModule;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Panitacraft extends JavaPlugin {
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Panitacraft is starting up!");

        // Messenger setup
        BukkitAudiences adventure = BukkitAudiences.create(this);
        Messenger.init(adventure);

        // Config setup
        Config.load(this);
        moduleManager = new ModuleManager(this);
        moduleManager.register(new OptimizationModule());
    }

    @Override
    public void onDisable() {
        if (moduleManager != null) moduleManager.disableAll();

        // Plugin shutdown logic
        getLogger().info("Panitacraft is shutting down!");
    }
}
