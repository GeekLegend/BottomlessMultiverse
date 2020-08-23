package fr.geeklegend.scheduler;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameStage;
import fr.geeklegend.game.GameState;
import fr.geeklegend.game.chest.ChestManager;
import fr.geeklegend.team.TeamManager;
import fr.geeklegend.util.Constant;
import fr.geeklegend.util.interfaces.IScheduler;
import fr.geeklegend.world.WorldManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartScheduler extends BukkitRunnable implements IScheduler
{

    private static ConfigManager configManager = Main.getPlugin().getConfigManager();

    private static FileConfiguration defaultConfig = configManager.getDefaultConfig();

    private GameManager gameManager = Main.getPlugin().getGameManager();

    @Getter
    // private static int timer = defaultConfig.getInt("schedulers.start.timer");
    @Setter
    private static int timer = 10 + 1;

    @Getter
    @Setter
    private static boolean isRunning = false;

    @Getter
    @Setter
    private static boolean isPaused;

    @Override
    public void run()
    {
        if (!isPaused)
        {
            if (gameManager.getPlayers().size() < defaultConfig.getInt("schedulers.start.minplayers"))
            {
                stop();

                gameManager.setGameState(GameState.WAITING);
                gameManager.getPlayers().stream().filter(player -> player != null).forEach(player -> player.sendMessage(defaultConfig.getString("messages.starting.cancelled").replace("%prefix%", Constant.PREFIX).replace("%online%", String.valueOf(defaultConfig.getInt("schedulers.start.minplayers"))).replace("&", "§")));
            } else
            {
                timer--;

                gameManager.getPlayers().stream().filter(player -> player != null).forEach(player -> player.setLevel(timer));

                if (timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1)
                {
                    gameManager.getPlayers().stream().filter(player -> player != null).forEach(player ->
                    {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        player.sendTitle(defaultConfig.getString("messages.starting.title.firstline").replace("&", "§"), defaultConfig.getString("messages.starting.title.secondline").replace("%timer%", String.valueOf(timer)).replace("&", "§"), 1, 20, 1);
                        player.sendMessage(defaultConfig.getString("messages.starting.chat").replace("%prefix%", Constant.PREFIX).replace("%timer%", String.valueOf(timer)).replace("&", "§"));
                    });
                } else if (timer == 0)
                {
                    WorldManager worldManager = gameManager.getWorldManager();
                    TeamManager teamManager = gameManager.getTeamManager();

                    stop();

                    worldManager.setBorder(GameStage.STAGE_1);

                    gameManager.setGameState(GameState.PRE_GAME);
                    gameManager.setGameStage(GameStage.STAGE_1);
                    gameManager.getPlayers().stream().filter(player -> player != null).forEach(player ->
                    {
                        teamManager.random(player);

                        gameManager.setup(player, GameState.PRE_GAME);
                    });

                    new ChestManager();
                    new CageScheduler().runTaskTimer(Main.getPlugin(), 20, 20);
                    new ActionBarScheduler().runTaskTimer(Main.getPlugin(), 20, 20);
                }
            }
        }
    }

    @Override
    public void stop()
    {
        cancel();
        reset();
        isRunning = false;
    }

    @Override
    public void reset()
    {
        timer = defaultConfig.getInt("schedulers.start.timer") + 1;

        gameManager.getPlayers().stream().filter(player -> player != null).forEach(player -> player.setLevel(0));
    }

}
