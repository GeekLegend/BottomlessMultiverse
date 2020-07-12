package fr.geeklegend.kit;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.inventory.InventoryManager;
import fr.geeklegend.util.Constant;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitManager
{
    @Getter
    private List<Kit> kits;

    @Getter
    private Map<Player, Kit> kit, editKit;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    @Getter
    @Setter
    private boolean isInEdit;

    public KitManager()
    {
        this.kits = new ArrayList<>();
        this.kit = new HashMap<>();
        this.editKit = new HashMap<>();
        this.isInEdit = false;

        loadKits();
    }

    private void loadKits()
    {
        FileConfiguration kitConfig = configManager.getKitConfig();
        ConfigurationSection section = kitConfig.getConfigurationSection("kits");

        if (section != null)
        {
            section.getKeys(false).stream().forEach(keys ->
            {
                String name = section.getString(keys + ".name");
                ChatColor nameColor = ChatColor.valueOf(section.getString(keys + ".namecolor").replace(" ", "_").toUpperCase());
                Material icon = Material.valueOf(section.getString(keys + ".icon").replace(" ", "_").toUpperCase());
                List<String> description = section.getStringList(keys + ".description");
                List<String> contents = section.getStringList(keys + ".contents");
                String permission = section.getString(keys + ".permission");

                if (name != null && nameColor != null && icon != null && contents != null && permission != null)
                {
                    kits.add(new Kit(name, nameColor, icon, description, permission));
                }
            });
        }
    }

    public void setupEditKit(Kit kit, Player player, KitEditState kitEditState)
    {
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        InventoryManager inventoryManager = Main.getPlugin().getInventoryManager();

        switch (kitEditState)
        {
            case LOAD:
                isInEdit = true;

                inventoryManager.savePlayerContents(player);

                kit.load(player);

                player.closeInventory();
                player.setAllowFlight(false);
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(defaultConfig.getString("messages.editkit.help").replace("%prefix%", Constant.PREFIX).replace("&", "§"));
                break;
            case SAVE:
                isInEdit = false;

                kit.save(player);

                inventoryManager.loadPlayerContents(player);

                player.setAllowFlight(true);
                player.setGameMode(GameMode.valueOf(defaultConfig.getString("join.gamemode").replace(" ", "_").toUpperCase()));
                player.sendMessage(defaultConfig.getString("messages.editkit.saved").replace("%prefix%", Constant.PREFIX).replace("%kitnamecolor%", String.valueOf(kit.getNameColor())).replace("%kitname%", kit.getName()).replace("&", "§"));
                break;
            default:
                break;
        }
    }
}
