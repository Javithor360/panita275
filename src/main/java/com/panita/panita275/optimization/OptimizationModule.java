package com.panita.panita275.optimization;

import com.panita.panita275.core.modules.PluginModule;

public class OptimizationModule implements PluginModule {
    private boolean enabled;
    public static String packageName = "com.panita.panita275.optimization";

    @Override
    public String id() {
        return "optimization";
    }

    @Override
    public String basePackage() {
        return packageName;
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
