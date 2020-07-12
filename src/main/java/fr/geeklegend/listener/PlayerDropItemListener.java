package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDropItemListener implements Listener
{
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        ItemStack item = event.getItemDrop().getItemStack();
        GameState gameState = Main.getPlugin().getGameManager().getGameState();

        if (item != null)
        {
            if (!gameState.equals(GameState.GAME))
            {
                event.setCancelled(true);
            }
        }
    }
}
