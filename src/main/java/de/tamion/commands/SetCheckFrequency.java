package de.tamion.commands;

import de.tamion.PingKick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class SetCheckFrequency implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("PingKick.setcheckfrequency")) {
            sender.sendMessage("You aren't allowed to execute this command");
            return false;
        }
        FileConfiguration config = PingKick.getPlugin().getConfig();
        if(args.length == 0) {
            sender.sendMessage("Currently set to " + config.getInt("frequency") + " ticks");
            return false;
        }
        try {
            config.set("frequency", Integer.parseInt(args[0]));
            PingKick.getPlugin().saveConfig();
            sender.sendMessage("Check frequency set to " + args[0] + " ticks");
        } catch (NumberFormatException e) {
            sender.sendMessage("Frequency must be an integer");
        }
        return false;
    }
}
