package fr.geeklegend.world;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameStage;
import fr.geeklegend.util.Util;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

public class WorldManager
{

    public static final World WORLD = Bukkit.getWorlds().get(0);

    @Getter
    private WorldBorder worldBorder;

    private ConfigManager configManager = Main.getPlugin().getConfigManager();

    private FileConfiguration defaultConfig = configManager.getDefaultConfig();

    public WorldManager()
    {
        this.worldBorder = WORLD.getWorldBorder();

        clearEntities();
        setup();
    }

    private void clearEntities()
    {
        WORLD.getEntities().stream().filter(entity -> entity != null && !entity.getType().equals(EntityType.PLAYER)).forEach(entity ->
        {
            entity.remove();
        });
    }

    private void setup()
    {
        WORLD.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        WORLD.setTime(1000);

        worldBorder.reset();
    }

    public void setBorder(GameStage gameStage)
    {
        worldBorder.setCenter(Util.getStringToLocation(defaultConfig.getString("game.world." + gameStage.getName() + ".border.center").replace(" ", "").split(",")));
        worldBorder.setSize(defaultConfig.getDouble("game.world." + gameStage.getName() + ".border.size"));
    }

    public void retractBorder()
    {
        worldBorder.setSize(worldBorder.getSize() - 1.0);
    }

}
