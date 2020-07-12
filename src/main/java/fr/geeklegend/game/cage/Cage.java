package fr.geeklegend.game.cage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;

@AllArgsConstructor
public class Cage
{

    @Getter
    private Material material;

    @Getter
    private Location spawn;

}
