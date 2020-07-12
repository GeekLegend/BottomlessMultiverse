package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.team.Team;
import fr.geeklegend.team.TeamManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntityListener implements Listener
{
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        Entity damaged = event.getEntity();
        Entity damager = event.getDamager();
        GameManager gameManager = Main.getPlugin().getGameManager();
        TeamManager teamManager = gameManager.getTeamManager();
        GameState gameState = gameManager.getGameState();

        if (gameState.equals(GameState.GAME))
        {
            if (damaged instanceof Player)
            {
                Player victim = (Player) damaged;
                Team victimTeam = teamManager.getTeam().get(victim);

                if (damager instanceof Player)
                {
                    Player killer = (Player) damager;

                    if (victimTeam != null && victimTeam.getPlayers().contains(killer.getName()))
                    {
                        event.setCancelled(true);
                    }
                } else if (damager instanceof Arrow)
                {
                    Arrow arrow = (Arrow) damager;
                    ProjectileSource projectileSource = arrow.getShooter();

                    if (projectileSource instanceof Player)
                    {
                        Player shooter = (Player) projectileSource;

                        if (victimTeam != null && victimTeam.getPlayers().contains(shooter.getName()))
                        {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
