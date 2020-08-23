package fr.geeklegend.command;

import fr.geeklegend.Main;
import fr.geeklegend.kit.Kit;
import fr.geeklegend.kit.KitEditState;
import fr.geeklegend.kit.KitManager;
import fr.geeklegend.scheduler.StartScheduler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveKitCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (player.isOp())
            {
                if (args.length == 0)
                {
                    KitManager kitManager = Main.getPlugin().getGameManager().getKitManager();

                    if (kitManager.getEditKit().containsKey(player))
                    {
                        Kit kit = kitManager.getEditKit().get(player);

                        StartScheduler.setPaused(false);

                        if (kit != null)
                        {
                            kitManager.setupEditKit(kit, player, KitEditState.SAVE);
                        }

                        kitManager.getEditKit().remove(player);
                    }
                }
            }
        }
        return false;
    }
}
