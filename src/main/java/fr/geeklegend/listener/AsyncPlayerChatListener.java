package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.game.spectator.SpectatorManager;
import fr.geeklegend.team.Team;
import fr.geeklegend.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener
{
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();
        TeamManager teamManager = gameManager.getTeamManager();
        SpectatorManager spectatorManager = gameManager.getSpectatorManager();
        GameState gameState = gameManager.getGameState();
        String message = event.getMessage();
        Team team = teamManager.getTeam().get(player);

        if (gameState.equals(GameState.GAME))
        {
            event.setCancelled(true);

            if (message.startsWith(defaultConfig.getString("messages.chat.global.prefix")))
            {
                gameManager.getPlayers().stream().filter(players -> gameManager.getPlayers().contains(player)).forEach(players -> players.sendMessage(defaultConfig.getString("messages.chat.text").replace(defaultConfig.getString("messages.chat.global.prefix"), "").replace("%playername%", player.getName()).replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%teamname%", team.getName()).replace("%message%", message).replace("&", "§")));
            } else
            {
                team.getPlayers().stream().filter(players -> players != null).forEach(players ->
                {
                    Player pl = Bukkit.getPlayer(players);

                    pl.sendMessage(defaultConfig.getString("messages.chat.text").replace("%playername%", player.getName()).replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%teamname%", team.getName()).replace("%message%", message).replace("&", "§"));
                });
            }

            spectatorManager.getSpectators().stream().filter(spectator -> spectatorManager.getSpectators().contains(player)).forEach(spectator -> spectator.sendMessage(defaultConfig.getString("messages.spectator").replace("%playername%", player.getName()).replace("%message%", message).replace("&", "§")));
        }
    }
}
