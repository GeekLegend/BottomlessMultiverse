package fr.geeklegend.scheduler;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameStage;
import fr.geeklegend.game.GameState;
import fr.geeklegend.game.chest.ChestManager;
import fr.geeklegend.team.TeamManager;
import fr.geeklegend.team.TeamType;
import fr.geeklegend.util.Constant;
import fr.geeklegend.util.interfaces.IScheduler;
import fr.geeklegend.world.WorldManager;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportationScheduler extends BukkitRunnable implements IScheduler
{

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    private GameManager gameManager = Main.getPlugin().getGameManager();

    private GameStage gameStage = gameManager.getGameStage();

    private int timer;

    public TeleportationScheduler()
    {
        this.timer = defaultConfig.getInt("schedulers.teleport.timer") + 1;
    }

    @Override
    public void run()
    {
        timer--;

        if (timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1)
        {
            gameManager.getPlayers().stream().filter(player -> player != null).forEach(player ->
            {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                if (!gameStage.equals(GameStage.STAGE_2))
                {
                    player.sendTitle(defaultConfig.getString("messages.teleport.title.firstline").replace("&", "§"), defaultConfig.getString("messages.teleport.title.secondline").replace("%timer%", String.valueOf(timer)).replace("&", "§"), 1, 20, 1);
                    player.sendMessage(defaultConfig.getString("messages.teleport.chat").replace("%prefix%", Constant.PREFIX).replace("%timer%", String.valueOf(timer)).replace("&", "§"));
                } else
                {
                    player.sendTitle(defaultConfig.getString("messages.deathmatch.title.firstline").replace("&", "§"), defaultConfig.getString("messages.deathmatch.title.secondline").replace("%timer%", String.valueOf(timer)).replace("&", "§"), 1, 20, 1);
                    player.sendMessage(defaultConfig.getString("messages.deathmatch.chat").replace("%prefix%", Constant.PREFIX).replace("%timer%", String.valueOf(timer)).replace("&", "§"));
                }
            });
        } else if (timer == 0)
        {
            WorldManager worldManager = gameManager.getWorldManager();

            stop();

            switch (gameStage)
            {
                case STAGE_1:
                    gameManager.setGameStage(GameStage.STAGE_2);
                    worldManager.setBorder(GameStage.STAGE_2);

                    setup();
                    break;
                case STAGE_2:
                    gameManager.setGameStage(GameStage.STAGE_3);
                    worldManager.setBorder(GameStage.STAGE_3);

                    setup();
                    break;
                case STAGE_3:
                    TeamManager teamManager = gameManager.getTeamManager();

                    gameManager.setGameStage(GameStage.DEATH_MATCH);

                    if (teamManager.getTeamType().equals(TeamType.SOLO))
                    {
                        if (teamManager.getTeams().isEmpty())
                        {
                            if (gameManager.getPlayers().size() == 2)
                            {

                            }
                        }
                    } else
                    {
                        if (teamManager.getTeams().size() == 2)
                        {

                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setup()
    {
        for (Player player : gameManager.getPlayers())
        {
            gameManager.setup(player, GameState.GAME);
        }

        new ChestManager();
        new CageScheduler().runTaskTimer(Main.getPlugin(), 20, 20);
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
        timer = defaultConfig.getInt("schedulers.teleport.timer") + 1;
    }

}
