package fr.geeklegend.team;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.util.Constant;
import fr.geeklegend.util.ItemBuilder;
import fr.geeklegend.util.interfaces.IInventory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class TeamInventory implements IInventory, Listener
{
    @Getter
    private int size;

    @Getter
    private String name;

    private Inventory inventory;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    private GameManager gameManager;

    private TeamManager teamManager;

    private TeamType teamType;

    private Team team;

    private ItemBuilder teamItem;

    public TeamInventory()
    {
        this.size = defaultConfig.getInt("inventory.teams.size");
        this.name = defaultConfig.getString("inventory.teams.name").replace("&", "§");
    }

    @Override
    public Inventory create(Player player)
    {
        inventory = Bukkit.createInventory(player, size, name);
        gameManager = Main.getPlugin().getGameManager();
        teamManager = gameManager.getTeamManager();
        teamType = teamManager.getTeamType();

        switch (teamType)
        {
            case DUO:
                for (int i = 0; i < 8; i++)
                {
                    team = teamManager.getTeams().get(i);
                    teamItem = new ItemBuilder(team.getIcon()).setName(team.getNameColor() + team.getName() + " (" + team.getPlayers().size() + "/" + team.getMaxInTeam() + ")");

                    for (String p : team.getPlayers())
                    {
                        teamItem.addLoreLine(defaultConfig.getString("teams.list.players").replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%playername%", p).replace("&", "§"));
                    }

                    inventory.addItem(teamItem.toItemStack());
                }
                break;
            case QUAD:
                for (int i = 0; i < 5; i++)
                {
                    team = teamManager.getTeams().get(i);
                    teamItem = new ItemBuilder(team.getIcon()).setName(team.getNameColor() + team.getName() + " (" + team.getPlayers().size() + "/" + team.getMaxInTeam() + ")");

                    for (String p : team.getPlayers())
                    {
                        teamItem.addLoreLine(defaultConfig.getString("teams.list.players").replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%playername%", p).replace("&", "§"));
                    }

                    inventory.addItem(teamItem.toItemStack());
                }
                break;
            default:
                break;
        }

        return inventory;
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        InventoryView inventoryView = event.getView();
        ItemStack item = event.getCurrentItem();
        gameManager = Main.getPlugin().getGameManager();
        GameState gameState = gameManager.getGameState();

        if (inventory != null && inventoryView.getTitle().equalsIgnoreCase(name))
        {
            if (item != null)
            {
                if (!gameState.equals(GameState.GAME))
                {
                    teamManager = gameManager.getTeamManager();

                    teamManager.getTeams().stream().filter(team -> team != null).forEach(team ->
                    {
                        if (item.getType().equals(team.getIcon()) && item.getItemMeta().getDisplayName().contains(team.getNameColor() + team.getName()))
                        {
                            if (team.getPlayers().contains(player.getName()))
                            {
                                player.sendMessage(defaultConfig.getString("messages.teams.already").replace("%prefix%", Constant.PREFIX).replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%teamname%", team.getName()).replace("&", "§"));
                            } else
                            {
                                Team lastTeam = teamManager.getTeam().get(player);

                                if (lastTeam != null)
                                {
                                    lastTeam.getPlayers().remove(player.getName());
                                    teamManager.getTeam().remove(player);
                                }

                                if (team.getPlayers().size() >= team.getMaxInTeam())
                                {
                                    player.sendMessage(defaultConfig.getString("messages.teams.maxplayers").replace("%prefix%", Constant.PREFIX).replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%teamname%", team.getName()).replace("&", "§"));
                                } else
                                {
                                    teamManager.getTeam().put(player, team);
                                    teamManager.getTeam().get(player).getPlayers().add(player.getName());

                                    player.sendMessage(defaultConfig.getString("messages.teams.joined").replace("%prefix%", Constant.PREFIX).replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%teamname%", team.getName()).replace("&", "§"));
                                }
                            }
                        }
                    });

                    update();
                }
            }
        }
    }

    @Override
    public void update()
    {
        Bukkit.getOnlinePlayers().stream().forEach(players ->
        {
            InventoryView inventoryView = players.getOpenInventory();

            if (inventoryView.getTitle().equalsIgnoreCase(name))
            {
                Inventory contents = inventoryView.getTopInventory();
                teamManager = gameManager.getTeamManager();
                teamType = teamManager.getTeamType();

                contents.clear();

                switch (teamType)
                {
                    case DUO:
                        for (int i = 0; i < 8; i++)
                        {
                            team = teamManager.getTeams().get(i);
                            teamItem = new ItemBuilder(team.getIcon()).setName(team.getNameColor() + team.getName() + " (" + team.getPlayers().size() + "/" + team.getMaxInTeam() + ")");

                            for (String p : team.getPlayers())
                            {
                                teamItem.addLoreLine(defaultConfig.getString("teams.list.players").replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%playername%", p).replace("&", "§"));
                            }

                            contents.addItem(teamItem.toItemStack());
                        }
                        break;
                    case QUAD:
                        for (int i = 0; i < 5; i++)
                        {
                            team = teamManager.getTeams().get(i);
                            teamItem = new ItemBuilder(team.getIcon()).setName(team.getNameColor() + team.getName() + " (" + team.getPlayers().size() + "/" + team.getMaxInTeam() + ")");

                            for (String p : team.getPlayers())
                            {
                                teamItem.addLoreLine(defaultConfig.getString("teams.list.players").replace("%teamnamecolor%", String.valueOf(team.getNameColor())).replace("%playername%", p).replace("&", "§"));
                            }

                            contents.addItem(teamItem.toItemStack());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
