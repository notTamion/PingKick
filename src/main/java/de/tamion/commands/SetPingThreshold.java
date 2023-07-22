package de.tamion.commands;

import de.tamion.PingKick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class SetPingThreshold implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("PingKick.setpingthreshold")) {
            sender.sendMessage("You aren't allowed to execute this command");
            return false;
        }
        FileConfiguration config = PingKick.getPlugin().getConfig();
        if(args.length == 0) {
            sender.sendMessage("Currently set to " + config.getInt("threshold") + " ms");
            return false;
        }
        try {
            config.set("threshold", Integer.parseInt(args[0]));
            PingKick.getPlugin().saveConfig();
            sender.sendMessage("Ping threshold set to " + args[0] + " ms");
        } catch (NumberFormatException e) {
            sender.sendMessage("Ping must be an integer");
        }
        return false;
    }
}
