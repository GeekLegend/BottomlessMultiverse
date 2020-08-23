package fr.geeklegend.scheduler;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.game.cage.CageManager;
import fr.geeklegend.util.Constant;
import fr.geeklegend.util.interfaces.IScheduler;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class CageScheduler extends BukkitRunnable implements IScheduler
{

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    private int timer;

    public CageScheduler()
    {
        this.timer = defaultConfig.getInt("schedulers.cage.timer") + 1;
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
                player.sendMessage(defaultConfig.getString("messages.cage").replace("%prefix%", Constant.PREFIX).replace("%timer%", String.valueOf(timer)).replace("&", "§"));
            });
        } else if (timer == 0)
        {
            CageManager cageManager = gameManager.getCageManager();

            stop();

            gameManager.setGameState(GameState.GAME);
            gameManager.getPlayers().stream().filter(player -> player != null).forEach(player ->
                    cageManager.getCages().stream().filter(cage -> cage != null).forEach(cage ->
                    {
                        cageManager.remove(cage);

                        player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
                    }));

            new BorderScheduler().runTaskTimer(Main.getPlugin(), 20, 20);
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
        timer = defaultConfig.getInt("schedulers.cage.timer");
    }

}
