package fr.geeklegend.game;

import com.google.common.collect.Lists;
import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.cage.Cage;
import fr.geeklegend.game.cage.CageManager;
import fr.geeklegend.game.spectator.SpectatorManager;
import fr.geeklegend.kit.KitManager;
import fr.geeklegend.player.PlayerData;
import fr.geeklegend.player.PlayerDataManager;
import fr.geeklegend.team.Team;
import fr.geeklegend.team.TeamManager;
import fr.geeklegend.team.TeamType;
import fr.geeklegend.util.ItemBuilder;
import fr.geeklegend.util.Util;
import fr.geeklegend.world.WorldManager;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager
{

    @Getter
    @Setter
    private GameState gameState;

    @Getter
    @Setter
    private GameStage gameStage;

    @Getter
    private WorldManager worldManager;

    @Getter
    private PlayerDataManager playerDataManager;

    @Getter
    private TeamManager teamManager;

    @Getter
    private KitManager kitManager;

    @Getter
    private EditItemManager editItemManager;

    @Getter
    private CageManager cageManager;

    @Getter
    private SpectatorManager spectatorManager;

    @Getter
    private List<Player> players;

    public GameManager()
    {
        this.gameState = GameState.WAITING;
        this.worldManager = new WorldManager();
        this.playerDataManager = new PlayerDataManager();
        this.teamManager = new TeamManager();
        this.kitManager = new KitManager();
        this.editItemManager = new EditItemManager();
        this.cageManager = new CageManager();
        this.spectatorManager = new SpectatorManager();
        this.players = new ArrayList<>();
    }

    public void setup(Player player, GameState gameState)
    {
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getInventory().clear();

        switch (gameState)
        {
            case WAITING:
                String[] location = defaultConfig.getString("join.spawn").replace(" ", "").split(",");

                player.setGameMode(GameMode.valueOf(defaultConfig.getString("join.gamemode").replace(" ", "_").toUpperCase()));
                player.teleport(Util.getStringToLocation(location));
                player.getInventory().setItem(defaultConfig.getInt("join.items.kits.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("join.items.kits.material").replace(" ", "_").toUpperCase()))
                        .setName(defaultConfig.getString("join.items.kits.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("join.items.kits.description"))).toItemStack());

                if (!teamManager.getTeamType().equals(TeamType.SOLO))
                {
                    player.getInventory().setItem(defaultConfig.getInt("join.items.teams.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("join.items.teams.material").replace(" ", "_").toUpperCase()))
                            .setName(defaultConfig.getString("join.items.teams.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("join.items.teams.description"))).toItemStack());
                }

                if (player.isOp())
                {
                    player.getInventory().setItem(defaultConfig.getInt("join.items.editkit.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("join.items.editkit.material").replace(" ", "_").toUpperCase()))
                            .setName(defaultConfig.getString("join.items.editkit.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("join.items.editkit.description"))).toItemStack());
                    player.getInventory().setItem(defaultConfig.getInt("join.items.edititems.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("join.items.edititems.material").replace(" ", "_").toUpperCase()))
                            .setName(defaultConfig.getString("join.items.edititems.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("join.items.edititems.description"))).toItemStack());
                }

                player.getInventory().setItem(defaultConfig.getInt("join.items.leave.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("join.items.leave.material").replace(" ", "_").toUpperCase()))
                        .setName(defaultConfig.getString("join.items.leave.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("join.items.leave.description"))).toItemStack());
                break;
            case PRE_GAME:
            case GAME:
                cageManager.loadCages();
                cageManager.teleport();

                player.setGameMode(GameMode.SURVIVAL);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30, 1));
                break;
            default:
                break;
        }
    }

    public void cleanUp(Player player)
    {
        Cage cage = cageManager.getCage().get(player);

        players.remove(player);
        playerDataManager.getPlayerData().remove(player);
        teamManager.getTeam().remove(player);
        kitManager.getKit().remove(player);
        kitManager.getEditKit().remove(player);
        editItemManager.getEditItems().remove(player);
        spectatorManager.getSpectators().remove(player);

        if (cage != null && !gameState.equals(GameState.WAITING))
        {
            cageManager.remove(cage);
        }
    }

    public Player getRandomPlayer()
    {
        List<Player> givenList = Lists.newArrayList(players);
        int randomIndex = new Random().nextInt(givenList.size());
        Player randomPlayer = givenList.get(randomIndex);

        givenList.remove(randomIndex);

        return randomPlayer != null ? randomPlayer : null;
    }
}
