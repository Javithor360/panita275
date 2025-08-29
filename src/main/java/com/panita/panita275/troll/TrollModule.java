package com.panita.panita275.troll;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.modules.PluginModule;
import com.panita.panita275.troll.util.DynamicItemCreation;
import org.bukkit.plugin.java.JavaPlugin;

public class TrollModule implements PluginModule {
    private boolean enabled;
    public static String packageName = "com.panita.panita275.troll";

    @Override
    public String id() {
        return "troll";
    }

    @Override
    public String basePackage() {
        return packageName;
    }

    @Override
    public void onEnable(JavaPlugin plugin) {
        if (Panitacraft.getModuleManager().isModuleActive("qol")) {
            DynamicItemCreation.registerIfMissing();
        }
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
