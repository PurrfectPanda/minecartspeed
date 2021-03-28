package me.purrfectpanda.minecartspeed;

import org.bukkit.plugin.java.JavaPlugin;

public final class Minecartspeed extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
