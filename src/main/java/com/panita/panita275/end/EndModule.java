package com.panita.panita275.end;

import com.panita.panita275.core.modules.PluginModule;

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
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean value) {
        enabled = value;
    }
}
