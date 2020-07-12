package fr.geeklegend.scheduler;

import fr.geeklegend.Main;
import fr.geeklegend.config.ConfigManager;
import fr.geeklegend.game.GameManager;
import fr.geeklegend.player.PlayerData;
import fr.geeklegend.player.PlayerDataManager;
import fr.geeklegend.util.interfaces.IScheduler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarScheduler extends BukkitRunnable implements IScheduler
{

    @Override
    public void run()
    {
        ConfigManager configManager = Main.getPlugin().getConfigManager();
        FileConfiguration defaultConfig = configManager.getDefaultConfig();
        GameManager gameManager = Main.getPlugin().getGameManager();

        gameManager.getPlayers().forEach(player ->
        {
            PlayerDataManager playerDataManager = gameManager.getPlayerDataManager();
            PlayerData playerData = playerDataManager.getPlayerData().get(player);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(defaultConfig.getString("messages.actionbar").replace("%kills%", String.valueOf(playerData.getKills())).replace("&", "§")));
        });
    }

    @Override
    public void stop()
    {
        cancel();
        reset();
    }

    @Override
    public void reset()
    {
    }

}
