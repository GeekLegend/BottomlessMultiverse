package fr.geeklegend.util.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface IInventory
{
    Inventory create(Player player);

    void onInventoryClick(InventoryClickEvent event);

    void update();
}
