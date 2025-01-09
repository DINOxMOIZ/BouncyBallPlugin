package org.ZenMatrix.bouncyBall;

import org.ZenMatrix.bouncyBall.Commands.BallCommand;
import org.ZenMatrix.bouncyBall.Listeners.SnowBallListeners;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BouncyBall extends JavaPlugin {



    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("ball").setExecutor(new BallCommand(this));
        getServer().getPluginManager().registerEvents(new SnowBallListeners(this),this);
        saveDefaultConfig();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
