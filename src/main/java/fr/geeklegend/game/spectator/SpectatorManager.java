package fr.geeklegend.game.spectator;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.util.ItemBuilder;
import fr.geeklegend.util.Util;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorManager
{

    @Getter
    private List<Player> spectators;

    public SpectatorManager()
    {
        this.spectators = new ArrayList<>();
    }

    public void setup(Player player)
    {
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();

        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(Util.getStringToLocation(defaultConfig.getString("join.spawn").replace(" ", "").split(",")));
        player.getInventory().clear();
        player.getInventory().setItem(defaultConfig.getInt("game.spectators.items.menu.slot"), new ItemBuilder(Material.valueOf(defaultConfig.getString("game.spectators.items.menu.material").replace(" ", "_").toUpperCase())).setName(defaultConfig.getString("game.spectators.items.menu.name").replace("&", "§")).setLore(Util.getListToString(defaultConfig.getStringList("game.spectators.items.menu.description"))).toItemStack());
    }
}
