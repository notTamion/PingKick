package de.tamion.commands;

import de.tamion.PingKick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class SetKickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("PingKick.setkickcommand")) {
            sender.sendMessage("You aren't allowed to execute this command");
            return false;
        }
        FileConfiguration config = PingKick.getPlugin().getConfig();
        if(args.length == 0) {
            sender.sendMessage("Currently set to " + config.getString("kickcommand"));
            return false;
        }
        config.set("kickcommand", String.join(" ", args));
        PingKick.getPlugin().saveConfig();
        sender.sendMessage("Kick command set to " + String.join(" ", args));
        return false;
    }
}
