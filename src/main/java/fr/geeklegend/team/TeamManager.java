package fr.geeklegend.team;

import com.google.common.collect.Lists;
import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.util.Constant;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamManager
{
    @Getter
    private List<Team> teams;

    @Getter
    private Map<Player, Team> team;

    @Getter
    private TeamType teamType;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    public TeamManager()
    {
        this.teams = new ArrayList<>();
        this.team = new HashMap<>();

        if (defaultConfig.getString("teams.type").equalsIgnoreCase("duo"))
        {
            this.teamType = TeamType.DUO;
        } else if (defaultConfig.getString("teams.type").equalsIgnoreCase("quad"))
        {
            this.teamType = TeamType.QUAD;
        } else
        {
            this.teamType = TeamType.SOLO;
        }

        loadTeams();
    }

    private void loadTeams()
    {
        List<String> tList = defaultConfig.getStringList("teams.list.team");

        tList.forEach(team ->
        {
            String[] parts = team.split(",");
            String name = parts[0];
            ChatColor nameColor = ChatColor.valueOf(parts[1].replace(" ", "_").toUpperCase());
            Material icon = Material.valueOf(parts[2].replace(" ", "_").toUpperCase());
            Team t = new Team(name, nameColor, icon);

            if (!teamType.equals(TeamType.SOLO))
            {
                switch (teamType)
                {
                    case DUO:
                        t.setMaxInTeam(2);
                        break;
                    case QUAD:
                        t.setMaxInTeam(4);
                        break;
                    default:
                        break;
                }

                teams.add(t);
            }
        });
    }

    public void random(Player player)
    {
        Team t = team.get(player);

        if (t == null)
        {
            t = getRandomTeam();

            if (t.getPlayers().size() != t.getMaxInTeam())
            {
                team.put(player, t);
                team.get(player).getPlayers().add(player.getName());

                player.sendMessage(defaultConfig.getString("messages.teams.joined").replace("%prefix%", Constant.PREFIX).replace("%teamnamecolor%", String.valueOf(t.getNameColor())).replace("%teamname%", t.getName()).replace("&", "§"));
            }
        }
    }

    public Team getRandomTeam()
    {
        List<Team> givenList = Lists.newArrayList(teams);
        int randomIndex = new Random().nextInt(givenList.size());
        Team randomTeam = givenList.get(randomIndex);

        givenList.remove(randomIndex);

        return randomTeam != null ? randomTeam : null;
    }
}
