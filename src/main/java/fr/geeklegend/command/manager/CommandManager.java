package fr.geeklegend.command.manager;

import fr.geeklegend.Main;
import fr.geeklegend.command.EnchantCommand;
import fr.geeklegend.command.SaveItemCommand;
import fr.geeklegend.command.SaveKitCommand;

public class CommandManager
{
    public CommandManager(Main plugin)
    {
        plugin.getCommand("savekit").setExecutor(new SaveKitCommand());
        plugin.getCommand("saveitems").setExecutor(new SaveItemCommand());
        plugin.getCommand("enchant").setExecutor(new EnchantCommand());
    }
}
