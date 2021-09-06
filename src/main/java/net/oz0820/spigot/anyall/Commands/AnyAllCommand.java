package net.oz0820.spigot.anyall.Commands;

import net.oz0820.spigot.anyall.AnyAllConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnyAllCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command anyAllCommand, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[AnyAll] " + ChatColor.RED + "This commands are only available when it are issued by the In-Game Players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("digUnder")) {
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("true")) {
                        AnyAllConfig.get().set("[CustomUser]." + player.getName() + ".digUnder", true);
                        AnyAllConfig.save();
                        player.sendMessage("[AnyAll] Dig Under " + ChatColor.GREEN + "TRUE");
                        return true;
                    } else if (args[1].equalsIgnoreCase("false")) {
                        AnyAllConfig.get().set("[CustomUser]." + player.getName() + ".digUnder", false);
                        AnyAllConfig.save();
                        player.sendMessage("[AnyAll] Dig Under " + ChatColor.RED + "FALSE");
                        return true;
                    }
                    player.performCommand("help anyall");
                    return true;
                }

            }
        }
        player.performCommand("help anyall");
        return true;
    }
}
