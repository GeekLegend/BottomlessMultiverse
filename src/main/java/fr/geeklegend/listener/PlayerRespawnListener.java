package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener
{
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        GameManager gameManager = Main.getPlugin().getGameManager();
        GameState gameState = gameManager.getGameState();

        if (gameState.equals(GameState.GAME))
        {
            event.setRespawnLocation(gameManager.getRandomPlayer().getLocation());
        }
    }
}
