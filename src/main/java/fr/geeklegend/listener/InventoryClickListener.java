package fr.geeklegend.listener;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.EditItemManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.game.spectator.SpectatorInventory;
import fr.geeklegend.kit.KitManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener
{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        InventoryType.SlotType slotType = event.getSlotType();
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();
        KitManager kitManager = gameManager.getKitManager();
        EditItemManager editItemManager = gameManager.getEditItemManager();
        GameState gameState = gameManager.getGameState();

        if (item != null)
        {
            if (!gameState.equals(GameState.GAME))
            {
                if (editItemManager.getEditItems().containsKey(player) && slotType.equals(InventoryType.SlotType.ARMOR))
                {
                    event.setCancelled(true);
                } else if (!kitManager.getEditKit().containsKey(player))
                {
                    if (editItemManager.getEditItems().containsKey(player))
                    {
                        return;
                    }

                    event.setCancelled(true);
                }
            } else
            {
                if (item.getType().equals(Material.valueOf(defaultConfig.getString("game.spectators.items.menu.material").replace(" ", "_").toUpperCase())))
                {
                    player.openInventory(new SpectatorInventory().create(player));
                }
            }
        }
    }
}
