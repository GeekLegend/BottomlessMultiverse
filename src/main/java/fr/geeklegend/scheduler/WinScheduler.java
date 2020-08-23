package fr.geeklegend.scheduler;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.util.Constant;
import fr.geeklegend.util.interfaces.IScheduler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class WinScheduler extends BukkitRunnable implements IScheduler
{

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    @Getter
    @Setter
    private int timer;

    public WinScheduler()
    {
        this.timer = defaultConfig.getInt("schedulers.win.timer");
    }

    @Override
    public void run()
    {
        timer--;

        if (timer == 0)
        {
            stop();

            Bukkit.shutdown();
        }
    }

    @Override
    public void stop()
    {
        cancel();
        reset();
    }

    @Override
    public void reset()
    {
        timer = defaultConfig.getInt("schedulers.win.timer");
    }

}
