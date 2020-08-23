package fr.geeklegend.team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Team
{

    @Getter
    private String name;

    @Getter
    private ChatColor nameColor;

    @Getter
    private Material icon;

    @Getter
    private List<String> players;

    @Getter
    @Setter
    private int maxInTeam;

    public Team(String name, ChatColor nameColor, Material icon)
    {
        this.name = name;
        this.nameColor = nameColor;
        this.icon = icon;
        this.players = new ArrayList<>();
    }

}
