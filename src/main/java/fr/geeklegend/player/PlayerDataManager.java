package fr.geeklegend.player;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager
{
    @Getter
    private Map<Player, PlayerData> playerData;

    public PlayerDataManager()
    {
        this.playerData = new HashMap<>();
    }
}
