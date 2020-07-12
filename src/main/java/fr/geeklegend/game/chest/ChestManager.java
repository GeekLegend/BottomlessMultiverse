package fr.geeklegend.game.chest;

import com.google.common.collect.Lists;
import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.EditItemManager;
import fr.geeklegend.world.WorldManager;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ChestManager
{

    public ChestManager()
    {
        refillChests();
    }

    public void refillChests()
    {
        for (Chunk chunk : WorldManager.WORLD.getLoadedChunks())
        {
            for (BlockState blockState : chunk.getTileEntities())
            {
                if (blockState instanceof Chest)
                {
                    Chest chest = (Chest) blockState.getBlock().getState();

                    if (chest != null)
                    {
                        Inventory inventory = chest.getInventory();

                        if (inventory != null)
                        {
                            ConfigManager configManager = Main.getPlugin().getConfigManager();
                            FileConfiguration defaultConfig = configManager.getDefaultConfig();

                            inventory.clear();

                            for (int i = 0; i < defaultConfig.getInt("game.chests.items"); i++)
                            {
                                ItemStack randomItem = getRandomItem();

                                if (randomItem != null)
                                {
                                    Random random = new Random();
                                    int sizeLimit = inventory.getSize() - 1;
                                    int randomSlot = random.nextInt(sizeLimit);

                                    inventory.setItem(randomSlot, randomItem);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public ItemStack getRandomItem()
    {
        EditItemManager editItemManager = Main.getPlugin().getGameManager().getEditItemManager();
        List<ItemStack> givenList = Lists.newArrayList(editItemManager.getItems());
        int randomIndex = new Random().nextInt(givenList.size());
        ItemStack randomItem = givenList.get(randomIndex);

        givenList.remove(randomIndex);

        if (randomItem != null)
        {
            return randomItem;
        }
        return null;
    }

}
