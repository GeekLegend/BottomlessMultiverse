package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.EditItemManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.inventory.EditItemInventory;
import fr.geeklegend.kit.KitEditInventory;
import fr.geeklegend.kit.KitInventory;
import fr.geeklegend.kit.KitManager;
import fr.geeklegend.scheduler.StartScheduler;
import fr.geeklegend.team.TeamInventory;
import fr.geeklegend.util.BungeeChannelApi;
import fr.geeklegend.util.Constant;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();
        GameState gameState = gameManager.getGameState();
        String cantEditMessage = defaultConfig.getString("messages.cantedit").replace("%prefix%", Constant.PREFIX).replace("&", "§");

        if (item != null)
        {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
            {
                if (!gameState.equals(GameState.GAME))
                {
                    if (item.getType().equals(Material.valueOf(defaultConfig.getString("join.items.kits.material").replace(" ", "_")
                            .toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("join.items.kits.name").replace("&", "§")))
                    {
                        player.openInventory(new KitInventory().create(player));
                    }
                    if (item.getType().equals(Material.valueOf(defaultConfig.getString("join.items.teams.material").replace(" ", "_")
                            .toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("join.items.teams.name").replace("&", "§")))
                    {
                        player.openInventory(new TeamInventory().create(player));
                    } else if (item.getType().equals(Material.valueOf(defaultConfig.getString("join.items.editkit.material").replace(" ", "_")
                            .toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("join.items.editkit.name").replace("&", "§")))
                    {
                        KitManager kitManager = gameManager.getKitManager();

                        if (kitManager.isInEdit())
                        {
                            player.sendMessage(cantEditMessage);
                        } else
                        {
                            StartScheduler.setPaused(true);

                            player.openInventory(new KitEditInventory().create(player));
                        }
                    } else if (item.getType().equals(Material.valueOf(defaultConfig.getString("join.items.edititems.material").replace(" ", "_")
                            .toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("join.items.edititems.name").replace("&", "§")))
                    {
                        EditItemManager editItemManager = gameManager.getEditItemManager();

                        if (editItemManager.isInEdit())
                        {
                            player.sendMessage(cantEditMessage);
                        } else
                        {
                            StartScheduler.setPaused(true);

                            player.openInventory(new EditItemInventory().create(player));
                        }
                    } else if (item.getType().equals(Material.valueOf(defaultConfig.getString("join.items.leave.material").replace(" ", "_")
                            .toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("join.items.leave.name").replace("&", "§")))
                    {
                        BungeeChannelApi bungeeChannelApi = Main.getPlugin().getBungeeChannelApi();
                        bungeeChannelApi.connect(player, defaultConfig.getString("join.items.leave.redirect"));
                    }
                }
            }
        }
    }
}
