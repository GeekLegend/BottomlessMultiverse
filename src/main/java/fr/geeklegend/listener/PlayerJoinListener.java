package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.game.spectator.SpectatorManager;
import fr.geeklegend.player.PlayerData;
import fr.geeklegend.player.PlayerDataManager;
import fr.geeklegend.scheduler.StartScheduler;
import fr.geeklegend.util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();
        SpectatorManager spectatorManager = gameManager.getSpectatorManager();
        GameState gameState = gameManager.getGameState();

        if (gameState.equals(GameState.WAITING))
        {
            PlayerDataManager playerDataManager = gameManager.getPlayerDataManager();
            playerDataManager.getPlayerData().put(player, new PlayerData(player, 0));

            gameManager.getPlayers().add(player);
            gameManager.setup(player, GameState.WAITING);

            if (gameManager.getPlayers().size() >= defaultConfig.getInt("schedulers.start.minplayers"))
            {
                if (!StartScheduler.isRunning())
                {
                    StartScheduler startScheduler = new StartScheduler();
                    startScheduler.setRunning(true);
                    startScheduler.runTaskTimer(Main.getPlugin(), 20, 20);
                }
            }

            event.setJoinMessage(defaultConfig.getString("messages.join").replace("%prefix%", Constant.PREFIX).replace("%playername%", player.getName()).replace("%online%", String.valueOf(gameManager.getPlayers().size())).replace("%maxonline%", String.valueOf(Bukkit.getMaxPlayers())).replace("&", "§"));
        } else
        {
            if (!spectatorManager.getSpectators().contains(player))
            {
                spectatorManager.getSpectators().add(player);
                spectatorManager.setup(player);

                player.sendMessage(defaultConfig.getString("messages.spectator").replace("%prefix%", Constant.PREFIX).replace("&", "§"));
            }

            event.setJoinMessage(null);
        }
    }
}
