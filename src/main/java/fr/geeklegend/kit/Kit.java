package fr.geeklegend.kit;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Kit
{
    @Getter
    private String name;

    @Getter

    private ChatColor nameColor;

    @Getter
    private Material icon;

    @Getter
    private List<String> description;

    @Getter
    private String permission;

    @Getter
    private Map<Kit, ItemStack[]> armorContents, contents;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration kitConfig = configManager.getKitConfig();

    public Kit(String name, ChatColor nameColor, Material icon, List<String> description, String permission)
    {
        this.name = name;
        this.nameColor = nameColor;
        this.icon = icon;
        this.description = description;
        this.permission = permission;
        this.armorContents = new HashMap<>();
        this.contents = new HashMap<>();
    }

    public void load(Player player)
    {
        List<ItemStack> acList = (List<ItemStack>) kitConfig
                .get("kits." + name.toLowerCase() + ".armorcontents");
        List<ItemStack> cList = (List<ItemStack>) kitConfig
                .get("kits." + name.toLowerCase() + ".contents");
        ItemStack[] ac = acList.toArray(new ItemStack[4]);
        ItemStack[] c = cList.toArray(new ItemStack[35]);

        if (ac != null)
        {
            player.getInventory().setArmorContents(ac);
        }

        if (c != null)
        {
            player.getInventory().setContents(c);
        }

        player.updateInventory();
    }

    public void save(Player player)
    {
        List<ItemStack> acList = new ArrayList<>();
        List<ItemStack> cList = new ArrayList<>();

        acList.clear();
        cList.clear();

        for (ItemStack ac : player.getInventory().getArmorContents())
        {
            if (ac != null)
            {
                acList.add(ac);
            }
        }

        for (ItemStack c : player.getInventory().getContents())
        {
            if (cList != null)
            {
                cList.add(c);
            }
        }

        kitConfig.set("kits." + name.toLowerCase() + ".armorcontents", acList);
        kitConfig.set("kits." + name.toLowerCase() + ".contents", cList);

        configManager.save(kitConfig, "kits");
    }
}
