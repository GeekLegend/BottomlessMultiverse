package fr.geeklegend.kit;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.util.ItemBuilder;
import fr.geeklegend.util.Util;
import fr.geeklegend.util.interfaces.IInventory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class KitEditInventory implements IInventory, Listener
{
    @Getter
    private int size;

    @Getter
    private String name;

    private Inventory inventory;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration kitConfig = configManager.getKitConfig();

    public KitEditInventory()
    {
        this.size = kitConfig.getInt("inventory.editkit.size");
        this.name = kitConfig.getString("inventory.editkit.name").replace("&", "§");
    }

    @Override
    public Inventory create(Player player)
    {
        inventory = Bukkit.createInventory(player, size, name);
        KitManager kitManager = Main.getPlugin().getGameManager().getKitManager();

        for (Kit kit : kitManager.getKits())
        {
            if (kit != null)
            {
                inventory.addItem(new ItemBuilder(kit.getIcon()).setName(kit.getNameColor() +  kit.getName()).setLore(Util.getListToString(kit.getDescription())).setItemFlags(false).toItemStack());
            }
        }

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
                    KitManager kitManager = gameManager.getKitManager();

                    for (Kit kit : kitManager.getKits())
                    {
                        if (kit != null)
                        {
                            if (item.getType().equals(kit.getIcon()) && item.getItemMeta().getDisplayName().equalsIgnoreCase(kit.getNameColor() + kit.getName().replace("&", "§")))
                            {
                                if (!kitManager.getEditKit().containsKey(player))
                                {
                                    kitManager.getEditKit().put(player, kit);
                                    kitManager.setupEditKit(kit, player, KitEditState.LOAD);
                                }
                            }
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
