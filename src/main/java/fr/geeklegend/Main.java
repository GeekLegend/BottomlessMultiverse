package fr.geeklegend;

import fr.geeklegend.command.manager.CommandManager;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.inventory.InventoryManager;
import fr.geeklegend.listener.manager.ListenerManager;
import fr.geeklegend.util.BungeeChannelApi;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

    @Getter
    private static Main plugin;

    @Getter
    private BungeeChannelApi bungeeChannelApi;

    @Getter
    private ConfigManager configManager;

    @Getter
    private InventoryManager inventoryManager;

    @Getter
    private GameManager gameManager;

    @Override
    public void onEnable()
    {
        plugin = this;

        bungeeChannelApi = BungeeChannelApi.of(this);

        configManager = new ConfigManager(this);
        inventoryManager = new InventoryManager(this);
        gameManager = new GameManager();

        new CommandManager(this);
        new ListenerManager(this);
    }

    @Override
    public void onDisable()
    {
        plugin = null;
    }
}
