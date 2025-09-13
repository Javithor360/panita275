package com.panita.panita275.end;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.modules.PluginModule;
import com.panita.panita275.end.util.DynamicItemCreation;
import org.bukkit.plugin.java.JavaPlugin;

public class EndModule implements PluginModule {
    private boolean enabled;
    public static String packageName = "com.panita.panita275.end";

    @Override
    public String id() {
        return "end";
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
        enabled = value;
    }
}
