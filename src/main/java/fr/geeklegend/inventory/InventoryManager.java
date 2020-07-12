package fr.geeklegend.inventory;

import fr.geeklegend.Main;
import fr.geeklegend.game.spectator.SpectatorInventory;
import fr.geeklegend.kit.Kit;
import fr.geeklegend.kit.KitEditInventory;
import fr.geeklegend.kit.KitInventory;
import fr.geeklegend.team.TeamInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager
{

    private Map<Player, ItemStack[]> playerArmorContents, playerContents;

    public InventoryManager(Main plugin)
    {
        this.playerArmorContents = new HashMap<>();
        this.playerContents = new HashMap<>();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new KitInventory(), plugin);
        pluginManager.registerEvents(new KitEditInventory(), plugin);
        pluginManager.registerEvents(new TeamInventory(), plugin);
        pluginManager.registerEvents(new EditItemInventory(), plugin);
        pluginManager.registerEvents(new EditItemEnchantInventory(), plugin);
        pluginManager.registerEvents(new SpectatorInventory(), plugin);
    }

    public void loadPlayerContents(Player player)
    {
        ItemStack[] pac = playerArmorContents.get(player);
        ItemStack[] pc = playerContents.get(player);

        player.getInventory().clear();

        if (pac != null)
        {
            player.getInventory().setArmorContents(pac);
        }

        if (pc != null)
        {
            player.getInventory().setContents(pc);
        }
    }

    public void savePlayerContents(Player player)
    {
        playerArmorContents.put(player, player.getInventory().getArmorContents());
        playerContents.put(player, player.getInventory().getContents());
    }

}
