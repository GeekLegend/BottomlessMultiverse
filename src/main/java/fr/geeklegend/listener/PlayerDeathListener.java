package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.player.PlayerData;
import fr.geeklegend.player.PlayerDataManager;
import fr.geeklegend.team.TeamManager;
import fr.geeklegend.util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener
{
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();
        GameState gameState = gameManager.getGameState();

        if (gameState.equals(GameState.GAME))
        {
            TeamManager teamManager = gameManager.getTeamManager();
            PlayerDataManager playerDataManager = gameManager.getPlayerDataManager();
            PlayerData playerDataKiller = playerDataManager.getPlayerData().get(killer);
            String message;

            event.setDroppedExp(0);
            event.getDrops().clear();

            teamManager.getTeam().remove(victim);

            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> victim.spigot().respawn());

            if (killer != null && playerDataKiller != null)
            {
                playerDataKiller.setKills(playerDataKiller.getKills() + 1);

                message = defaultConfig.getString("messages.death.killer").replace("%prefix%", Constant.PREFIX).replace("%victimname%", victim.getName()).replace("%killername%", killer.getName()).replace("&", "§");
            } else
            {
                message = defaultConfig.getString("messages.death.void").replace("%prefix%", Constant.PREFIX).replace("%victimname%", victim.getName()).replace("&", "§");
            }

            event.setDeathMessage(message);
        }
    }
}
