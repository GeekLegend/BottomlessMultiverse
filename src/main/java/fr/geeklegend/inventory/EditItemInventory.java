package fr.geeklegend.inventory;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.*;
import fr.geeklegend.util.ItemBuilder;
import fr.geeklegend.util.Util;
import fr.geeklegend.util.interfaces.IInventory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class EditItemInventory implements IInventory, Listener
{
    @Getter
    private int size;

    @Getter
    private String name;

    private Inventory inventory;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    public EditItemInventory()
    {
        this.size = defaultConfig.getInt("inventory.edititems.size");
        this.name = defaultConfig.getString("inventory.edititems.name").replace("&", "§");
    }

    @Override
    public Inventory create(Player player)
    {
        inventory = Bukkit.createInventory(player, size, name);
        inventory.setItem(defaultConfig.getInt("inventory.edititems.items.stage1.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("inventory.edititems.items.stage1.material").replace(" ", "_").toUpperCase())).setName(defaultConfig.getString("inventory.edititems.items.stage1.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("inventory.edititems.items.stage1.description"))).setItemFlags(false).toItemStack());
        inventory.setItem(defaultConfig.getInt("inventory.edititems.items.stage2.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("inventory.edititems.items.stage2.material").replace(" ", "_").toUpperCase())).setName(defaultConfig.getString("inventory.edititems.items.stage2.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("inventory.edititems.items.stage2.description"))).setItemFlags(false).toItemStack());
        inventory.setItem(defaultConfig.getInt("inventory.edititems.items.stage3.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("inventory.edititems.items.stage3.material").replace(" ", "_").toUpperCase())).setName(defaultConfig.getString("inventory.edititems.items.stage3.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("inventory.edititems.items.stage3.description"))).setItemFlags(false).toItemStack());

        return inventory;
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        InventoryView inventoryView = event.getView();
        ItemStack item = event.getCurrentItem();
        GameManager gameManager = Main.getPlugin().getGameManager();
        GameState gameState = gameManager.getGameState();

        if (inventory != null && inventoryView.getTitle().equalsIgnoreCase(name))
        {
            if (item != null)
            {
                if (!gameState.equals(GameState.GAME))
                {
                    EditItemManager editItemManager = gameManager.getEditItemManager();

                    if (item.getType().equals(Material.valueOf(defaultConfig.getString("inventory.edititems.items.stage1.material").replace(" ", "_").toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("inventory.edititems.items.stage1.name").replace("&", "§")))
                    {
                        editItemManager.setGameStage(GameStage.STAGE_1);

                        if (!editItemManager.getEditItems().containsKey(player))
                        {
                            editItemManager.getEditItems().put(player, EditItemState.LOAD);
                            editItemManager.setupEditItems(player, EditItemState.LOAD);
                        }

                    } else if (item.getType().equals(Material.valueOf(defaultConfig.getString("inventory.edititems.items.stage2.material").replace(" ", "_").toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("inventory.edititems.items.stage2.name").replace("&", "§")))
                    {
                        editItemManager.setGameStage(GameStage.STAGE_2);

                        if (!editItemManager.getEditItems().containsKey(player))
                        {
                            editItemManager.getEditItems().put(player, EditItemState.LOAD);
                            editItemManager.setupEditItems(player, EditItemState.LOAD);
                        }
                    } else if (item.getType().equals(Material.valueOf(defaultConfig.getString("inventory.edititems.items.stage3.material").replace(" ", "_").toUpperCase())) && item.getItemMeta().getDisplayName().equalsIgnoreCase(defaultConfig.getString("inventory.edititems.items.stage3.name").replace("&", "§")))
                    {
                        editItemManager.setGameStage(GameStage.STAGE_3);

                        if (!editItemManager.getEditItems().containsKey(player))
                        {
                            editItemManager.getEditItems().put(player, EditItemState.LOAD);
                            editItemManager.setupEditItems(player, EditItemState.LOAD);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update()
    {

    }
}
