package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();
        GameState gameState = gameManager.getGameState();

        gameManager.cleanUp(player);

        if (gameState.equals(GameState.WAITING))
        {
            event.setQuitMessage(defaultConfig.getString("messages.quit").replace("%prefix%", Constant.PREFIX).replace("%playername%", player.getName()).replace("%online%", String.valueOf(gameManager.getPlayers().size())).replace("%maxonline%", String.valueOf(Bukkit.getMaxPlayers())).replace("&", "§"));
        } else {
            event.setQuitMessage(null);
        }
    }
}
