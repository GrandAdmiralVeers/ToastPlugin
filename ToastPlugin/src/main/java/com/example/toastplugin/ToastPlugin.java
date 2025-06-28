package com.example.toastplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class ToastPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("toast").setExecutor(new ToastCommand());
    }
}
