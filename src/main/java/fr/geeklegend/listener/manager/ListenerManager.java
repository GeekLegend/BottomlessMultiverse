package fr.geeklegend.listener.manager;

import fr.geeklegend.Main;
import fr.geeklegend.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager
{
    public ListenerManager(Main plugin)
    {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(), plugin);
        pluginManager.registerEvents(new PlayerDropItemListener(), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(), plugin);
        pluginManager.registerEvents(new PlayerMoveListener(), plugin);
        pluginManager.registerEvents(new PlayerDeathListener(), plugin);
        pluginManager.registerEvents(new PlayerRespawnListener(), plugin);
        pluginManager.registerEvents(new BlockBreakListener(), plugin);
        pluginManager.registerEvents(new BlockPlaceListener(), plugin);
        pluginManager.registerEvents(new WeatherChangeListener(), plugin);
        pluginManager.registerEvents(new EntityExplodeListener(), plugin);
        pluginManager.registerEvents(new EntityDamageListener(), plugin);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), plugin);
        pluginManager.registerEvents(new PortalCreateListener(), plugin);
        pluginManager.registerEvents(new InventoryClickListener(), plugin);
        pluginManager.registerEvents(new FoodLevelChangeListener(), plugin);
        pluginManager.registerEvents(new CreatureSpawnListener(), plugin);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), plugin);
    }
}
