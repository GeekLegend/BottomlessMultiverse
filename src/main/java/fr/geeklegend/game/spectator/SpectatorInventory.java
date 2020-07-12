package fr.geeklegend.game.spectator;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class SpectatorInventory implements IInventory, Listener
{
    @Getter
    private int size;

    @Getter
    private String name;

    private Inventory inventory;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    private GameManager gameManager = Main.getPlugin().getGameManager();

    public SpectatorInventory()
    {
        this.size = defaultConfig.getInt("inventory.spectator.size");
        this.name = defaultConfig.getString("inventory.spectator.name").replace("&", "§");
    }

    @Override
    public Inventory create(Player player)
    {
        inventory = Bukkit.createInventory(player, size, name);

        gameManager.getPlayers().stream().filter(players -> players != null && players != player).forEach(players -> inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(players.getName()).setName(defaultConfig.getString("inventory.spectator.items.players.name").replace("%playername%", players.getName()).replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("inventory.spectator.items.players.description"))).setItemFlags(false).toItemStack()));

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
        ClickType clickType = event.getClick();

        if (inventory != null && inventoryView.getTitle().equalsIgnoreCase(name))
        {
            if (item != null)
            {
                event.setCancelled(true);

                gameManager.getPlayers().stream().filter(players -> players != null).forEach(players ->
                {
                    if (clickType.isLeftClick() && item.getType().equals(Material.PLAYER_HEAD) && item.getItemMeta().getDisplayName().equalsIgnoreCase(players.getName().replace("&", "§")))
                    {
                        player.closeInventory();
                        player.teleport(players.getLocation().add(0.0, 1.0, 0.0));
                    }
                });
            }
        }
    }

    @Override
    public void update()
    {

    }
}
