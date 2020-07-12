package fr.geeklegend.util;

import fr.geeklegend.Main;

public class Constant
{
    public static final String PREFIX = Main.getPlugin().getConfigManager().getDefaultConfig().getString("messages.prefix").replace("&", "§");
}
