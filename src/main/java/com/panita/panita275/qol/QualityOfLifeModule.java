package com.panita.panita275.qol;

import com.panita.panita275.core.modules.PluginModule;

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
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean value) {
        this.enabled = value;
    }
}
