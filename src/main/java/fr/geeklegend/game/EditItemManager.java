package fr.geeklegend.game;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.inventory.InventoryManager;
import fr.geeklegend.util.Constant;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditItemManager
{

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    @Getter
    @Setter
    private GameStage gameStage;

    @Getter
    private Map<Player, EditItemState> editItems;

    @Getter
    private List<ItemStack> items;

    @Getter
    @Setter
    private boolean isInEdit;

    public EditItemManager()
    {
        this.editItems = new HashMap<>();
        this.items = new ArrayList<>();
        this.isInEdit = false;

        load();
    }

    public void load()
    {
        List<ItemStack> cList = (List<ItemStack>) defaultConfig
                .get("game.chests.stage1.list");

        for (ItemStack item : cList)
        {
            if (item != null)
            {
                items.add(item);
            }
        }
    }

    public void load(Player player)
    {
        List<ItemStack> cList = null;

        switch (gameStage)
        {
            case STAGE_1:
                cList = (List<ItemStack>) defaultConfig
                        .get("game.chests.stage1.list");
                break;
            case STAGE_2:
                cList = (List<ItemStack>) defaultConfig
                        .get("game.chests.stage2.list");
                break;
            case STAGE_3:
                cList = (List<ItemStack>) defaultConfig
                        .get("game.chests.stage3.list");
                break;
            default:
                break;
        }

        ItemStack[] c = cList.toArray(new ItemStack[35]);

        if (c != null)
        {
            player.getInventory().setContents(c);
        }
    }

    public void save(Player player)
    {
        List<ItemStack> cList = new ArrayList<>();

        cList.clear();

        for (ItemStack c : player.getInventory().getContents())
        {
            if (cList != null)
            {
                cList.add(c);
            }
        }

        defaultConfig.set("game.chests." + gameStage.getName() + ".list", cList);

        configManager.save(defaultConfig, "config");
    }


    public void setupEditItems(Player player, EditItemState editItemState)
    {
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        InventoryManager inventoryManager = Main.getPlugin().getInventoryManager();

        switch (editItemState)
        {
            case LOAD:
                isInEdit = true;

                inventoryManager.savePlayerContents(player);

                load(player);

                player.closeInventory();
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(defaultConfig.getString("messages.edititems.help").replace("%prefix%", Constant.PREFIX).replace("&", "§"));
                break;
            case SAVE:
                isInEdit = false;

                save(player);

                inventoryManager.loadPlayerContents(player);

                player.setGameMode(GameMode.valueOf(defaultConfig.getString("join.gamemode").replace(" ", "_").toUpperCase()));
                player.sendMessage(defaultConfig.getString("messages.edititems.saved").replace("%prefix%", Constant.PREFIX).replace("&", "§"));
                break;
            default:
                break;
        }
    }

}
