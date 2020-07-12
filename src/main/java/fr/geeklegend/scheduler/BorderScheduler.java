package fr.geeklegend.scheduler;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameStage;
import fr.geeklegend.util.Constant;
import fr.geeklegend.util.interfaces.IScheduler;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderScheduler extends BukkitRunnable implements IScheduler
{

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    private int timer;

    public BorderScheduler()
    {
        this.timer = defaultConfig.getInt("schedulers.border.timer") + 1;
    }

    @Override
    public void run()
    {
        GameManager gameManager = Main.getPlugin().getGameManager();

        timer--;

        if (timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1)
        {
            gameManager.getPlayers().stream().filter(player -> player != null).forEach(player ->
            {
                player.sendTitle(defaultConfig.getString("messages.border.title.firstline").replace("&", "§"), defaultConfig.getString("messages.border.title.secondline").replace("%timer%", String.valueOf(timer)).replace("&", "§"), 1, 20, 1);
                player.sendMessage(defaultConfig.getString("messages.border.chat").replace("%prefix%", Constant.PREFIX).replace("%timer%", String.valueOf(timer)).replace("&", "§"));
            });
        } else if (timer == 0)
        {
            stop();

            if (!gameManager.getGameStage().equals(GameStage.DEATH_MATCH))
            {
                new BorderRetractScheduler().runTaskTimer(Main.getPlugin(), 20, 20);
            }
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
        timer = defaultConfig.getInt("schedulers.border.timer") + 1;
    }

}
