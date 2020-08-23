package fr.geeklegend.command;

import fr.geeklegend.Main;
import fr.geeklegend.game.EditItemManager;
import fr.geeklegend.inventory.EditItemEnchantInventory;
import fr.geeklegend.scheduler.StartScheduler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchantCommand implements CommandExecutor
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
                    EditItemManager editItemManager = Main.getPlugin().getGameManager().getEditItemManager();

                    if (editItemManager.getEditItems().containsKey(player))
                    {
                        if (!player.getItemInHand().getType().equals(Material.AIR))
                        {
                            StartScheduler.setPaused(true);

                            player.openInventory(new EditItemEnchantInventory().create(player));
                        }
                    }
                }
            }
        }
        return false;
    }
}
