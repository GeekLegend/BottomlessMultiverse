package fr.geeklegend.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PlayerData
{
    @Getter
    private Player player;

    @Getter
    @Setter
    private int kills;
}
