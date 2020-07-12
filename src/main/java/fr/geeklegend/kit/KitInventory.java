package fr.geeklegend.kit;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.util.Constant;
import fr.geeklegend.util.ItemBuilder;
import fr.geeklegend.util.LuckPermsHelper;
import fr.geeklegend.util.Util;
import fr.geeklegend.util.interfaces.IInventory;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataType;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class KitInventory implements IInventory, Listener
{
    @Getter
    private int size;

    @Getter
    private String name;

    private Inventory inventory;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    private FileConfiguration kitConfig = configManager.getKitConfig();

    public KitInventory()
    {
        this.size = kitConfig.getInt("inventory.kits.size");
        this.name = kitConfig.getString("inventory.kits.name").replace("&", "§");
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
                inventory.addItem(new ItemBuilder(kit.getIcon()).setName(kit.getNameColor() + kit.getName()).setLore(Util.getListToString(kit.getDescription())).setItemFlags(false).toItemStack());
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
                                User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());

                                if (user != null)
                                {
                                    if (LuckPermsHelper.hasPermission(user, kit.getPermission()))
                                    {
                                        if (!kitManager.getKit().containsKey(player))
                                        {
                                            kitManager.getKit().put(player, kit);

                                            player.sendMessage(defaultConfig.getString("messages.kit.selected").replace("%prefix%", Constant.PREFIX).replace("%kitnamecolor%", String.valueOf(kit.getNameColor())).replace("%kitname%", kit.getName()).replace("&", "§"));
                                        } else
                                        {
                                            player.sendMessage(defaultConfig.getString("messages.kit.already").replace("%prefix%", Constant.PREFIX).replace("%kitnamecolor%", String.valueOf(kit.getNameColor())).replace("%kitname%", kit.getName()).replace("&", "§"));
                                        }
                                    } else
                                    {
                                        player.sendMessage(defaultConfig.getString("messages.kit.have").replace("%prefix%", Constant.PREFIX).replace("%kitnamecolor%", String.valueOf(kit.getNameColor())).replace("%kitname%", kit.getName()).replace("&", "§"));
                                    }
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
