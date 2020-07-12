package fr.geeklegend.util;

import fr.geeklegend.world.WorldManager;
import org.bukkit.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Util
{

    public static Location getStringToLocation(String[] string)
    {
        return new Location(WorldManager.WORLD, Double.valueOf(string[0]), Double.valueOf(string[1]), Double.valueOf(string[2]), Float.valueOf(string[3]), Float.valueOf(string[4]));
    }

    public static List<String> getListToString(List<String> list)
    {
        List<String> newList = new ArrayList<>();

        newList.clear();

        for (String lore : list)
        {
            newList.add(lore.replace("&", "§"));
        }
        return newList;
    }

    public static String getTimerFormat(String pattern, int timer)
    {
        return new SimpleDateFormat(pattern).format(Integer.valueOf(timer * 1000));
    }
}
