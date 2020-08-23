package fr.geeklegend.inventory;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.game.GameState;
import fr.geeklegend.scheduler.StartScheduler;
import fr.geeklegend.util.ItemBuilder;
import fr.geeklegend.util.interfaces.IInventory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditItemEnchantInventory implements IInventory, Listener
{
    @Getter
    private int size;

    @Getter
    private String name;

    private Inventory inventory;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    public EditItemEnchantInventory()
    {
        this.size = defaultConfig.getInt("inventory.edititems.enchants.size");
        this.name = defaultConfig.getString("inventory.edititems.enchants.name").replace("&", "§");
    }

    @Override
    public Inventory create(Player player)
    {
        inventory = Bukkit.createInventory(player, size, name);
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Tranchant I").addBookEnchantment(Enchantment.DAMAGE_ALL, 1).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Tranchant II").addBookEnchantment(Enchantment.DAMAGE_ALL, 2).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Tranchant III").addBookEnchantment(Enchantment.DAMAGE_ALL, 3).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Tranchant IV").addBookEnchantment(Enchantment.DAMAGE_ALL, 4).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Tranchant V").addBookEnchantment(Enchantment.DAMAGE_ALL, 5).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Protection I").addBookEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Protection II").addBookEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Protection III").addBookEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Puissance I").addBookEnchantment(Enchantment.ARROW_DAMAGE, 1).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Puissance II").addBookEnchantment(Enchantment.ARROW_DAMAGE, 2).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Infinité I").addBookEnchantment(Enchantment.ARROW_INFINITE, 1).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Solidité I").addBookEnchantment(Enchantment.DURABILITY, 1).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Epine I").addBookEnchantment(Enchantment.THORNS, 1).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Epine II").addBookEnchantment(Enchantment.THORNS, 2).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Flame I").addBookEnchantment(Enchantment.ARROW_FIRE, 1).toItemStack());
        inventory.addItem(new ItemBuilder(Material.ENCHANTED_BOOK).setName("Aura de feu I").addBookEnchantment(Enchantment.FIRE_ASPECT, 1).toItemStack());

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
                    if (item.getType().equals(Material.ENCHANTED_BOOK))
                    {
                        event.setCancelled(true);

                        ItemStack itemInHand = player.getItemInHand();

                        if (itemInHand != null)
                        {
                            ItemMeta itemInHandMeta = itemInHand.getItemMeta();

                            if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Tranchant I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Tranchant II"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Tranchant III"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Tranchant IV"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Tranchant V"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Protection I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Protection II"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Protection III"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Puissance I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Puissance II"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Infinité I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Solidité I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.DURABILITY, 1, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Epine I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.THORNS, 1, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Epine II"))
                            {
                                itemInHandMeta.addEnchant(Enchantment.THORNS, 2, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Flame I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.ARROW_FIRE, 1, false);
                            } else if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Aura de feu I"))
                            {
                               itemInHandMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
                            }

                            itemInHand.setItemMeta(itemInHandMeta);

                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        Inventory inventory = event.getInventory();
        InventoryView inventoryView = event.getView();

        if (inventory != null && inventoryView.getTitle().equalsIgnoreCase(name))
        {
            StartScheduler.setPaused(false);
        }
    }

    @Override
    public void update()
    {

    }
}
