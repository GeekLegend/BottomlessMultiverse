package fr.geeklegend.game.cage;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameStage;
import fr.geeklegend.team.TeamManager;
import fr.geeklegend.team.TeamType;
import fr.geeklegend.world.WorldManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CageManager
{

    @Getter
    private List<Cage> cages;

    @Getter
    private Map<Player, Cage> cage;

    private GameManager gameManager;

    private TeamManager teamManager;

    public CageManager()
    {
        this.cages = new ArrayList<>();
        this.cage = new HashMap<>();
    }

    public void loadCages()
    {
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        gameManager = Main.getPlugin().getGameManager();
        GameStage gameStage = gameManager.getGameStage();
        List<String> cList = defaultConfig.getStringList("game.cages." + gameStage.getName() + ".list");
        Material material = Material.valueOf(defaultConfig.getString("game.cages." + gameStage.getName() + ".material").replace(" ", "_").toUpperCase());

        cList.stream().forEach(cage ->
        {
            String[] parts = cage.split(",");
            Location spawn = new Location(WorldManager.WORLD, Double.valueOf(parts[0]), Double.valueOf(parts[1]), Double.valueOf(parts[2]), Float.valueOf(parts[3]), Float.valueOf(parts[4]));

            if (material != null && spawn != null)
            {
                cages.clear();
                cages.add(new Cage(material, spawn));
            }
        });
    }

    public void teleport()
    {
        gameManager = Main.getPlugin().getGameManager();
        gameManager.getPlayers().stream().filter(player -> player != null).forEach(player ->
        {
            for (int i = 0; i < cages.size(); i++)
            {
                Cage allCages = cages.get(i);

                cage.put(player, allCages);

                create(allCages);
            }

            Cage c = cage.get(player);

            if (c != null)
            {
                Location cSpawn = c.getSpawn();

                player.teleport(new Location(cSpawn.getWorld(), cSpawn.getX(), cSpawn.getY() + 1, cSpawn.getZ(), cSpawn.getYaw(), cSpawn.getPitch()));
            }
        });
    }

    private void create(Cage cage)
    {
        Location spawn = cage.getSpawn();
        Block block;

        if (spawn != null)
        {
            teamManager = gameManager.getTeamManager();
            block = spawn.getBlock();

            setMaterial(block, cage.getMaterial(), false);

            if (!teamManager.getTeamType().equals(TeamType.SOLO))
            {
                Location spawn2 = new Location(spawn.getWorld(), spawn.getX() + 1, spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());

                if (spawn2 != null)
                {
                    block = spawn2.getBlock();

                    setMaterial(block, cage.getMaterial(), false);
                }
            }
        }
    }

    public void remove(Cage cage)
    {
        Location spawn = cage.getSpawn();
        Block block;

        if (spawn != null)
        {
            teamManager = gameManager.getTeamManager();
            block = spawn.getBlock();

            setMaterial(block, cage.getMaterial(), true);

            if (!teamManager.getTeamType().equals(TeamType.SOLO))
            {
                Location spawn2 = new Location(spawn.getWorld(), spawn.getX() + 1, spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());

                if (spawn2 != null)
                {
                    block = spawn2.getBlock();

                    setMaterial(block, cage.getMaterial(), true);
                }
            }
        }
    }

    private void setMaterial(Block block, Material material, boolean isRemoved)
    {
        if (block != null)
        {
            if (isRemoved)
            {
                block.setType(Material.AIR);
            } else
            {
                block.setType(material);
            }
        }
    }

}