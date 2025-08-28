package com.panita.panita275.qol;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.modules.PluginModule;
import com.panita.panita275.qol.util.CoordinatesManager;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public class QualityOfLifeModule implements PluginModule {
    private boolean enabled;
    public static String packageName = "com.panita.panita275.qol";

    @Override
    public String id() {
        return "qol";
    }

    @Override
    public String basePackage() {
        return packageName;
    }

    @Override
    public void onEnable(JavaPlugin plugin) {
        CoordinatesManager.init(plugin);
        CustomItemManager.init(plugin.getDataFolder());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean value) {
        this.enabled = value;
    }
}
