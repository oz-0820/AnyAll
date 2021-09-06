package net.oz0820.spigot.anyall.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnyAllTabCompletion implements TabCompleter {
    public AnyAllTabCompletion() {
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(Arrays.asList(
                    "digUnder"
            ));
        }

        if (args[0].equalsIgnoreCase("digUnder")) {
            return new ArrayList<>(Arrays.asList(
                    "true", "false"
            ));
        }

        return new ArrayList<>();
    }

}
