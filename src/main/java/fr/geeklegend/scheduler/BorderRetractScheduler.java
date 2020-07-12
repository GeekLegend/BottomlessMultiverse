package fr.geeklegend.scheduler;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameStage;
import fr.geeklegend.util.interfaces.IScheduler;
import fr.geeklegend.world.WorldManager;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderRetractScheduler extends BukkitRunnable implements IScheduler
{

    @Override
    public void run()
    {
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();
        WorldManager worldManager = gameManager.getWorldManager();
        WorldBorder worldBorder = worldManager.getWorldBorder();
        GameStage gameStage = gameManager.getGameStage();
        double maxSize = defaultConfig.getDouble("game.world." + gameStage.getName() + ".border.maxsize");

        if (worldBorder.getSize() != maxSize)
        {
            worldManager.retractBorder();
        } else
        {
            stop();

            new TeleportationScheduler().runTaskTimer(Main.getPlugin(), 20, 20);
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
    }

}
