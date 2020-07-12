package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.util.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener
{
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        ConfigManager configManager =  Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameState gameState = Main.getPlugin().getGameManager().getGameState();

        if (gameState.equals(GameState.WAITING))
        {
            if (player.getLocation().getY() <= defaultConfig.getInt("join.void"))
            {
                String[] location = defaultConfig.getString("join.spawn").replace(" ", "").split(",");

                player.teleport(Util.getStringToLocation(location));
            }
        } else if (gameState.equals(GameState.PRE_GAME))
        {
            event.setTo(event.getFrom());
        } else if (gameState.equals(GameState.GAME))
        {

        }
    }
}
