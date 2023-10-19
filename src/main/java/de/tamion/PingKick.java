package de.tamion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PingKick extends JavaPlugin implements CommandExecutor, TabCompleter {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("pingkick").setExecutor(this);
        getCommand("pingkick").setTabCompleter(this);
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (p.hasPermission("pingkick.bypass") || p.getPing() <= getConfig().getLong("max"))
                        return;
                    Bukkit.getScheduler().callSyncMethod(getPlugin(PingKick.class), () -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("command").replaceAll("\\{name}", p.getName()).replaceAll("\\{ip}", p.getAddress().getAddress().getHostAddress()).replaceAll("\\{uuid}", String.valueOf(p.getUniqueId())).replaceAll("\\{ping}", String.valueOf(p.getPing())).replaceAll("\\{max}", getConfig().getString("max")));
                        return null;
                    });
                });
            }
        }.runTaskTimerAsynchronously(this, 20L, getConfig().getLong("period"));
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        FileConfiguration config = this.getConfig();
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(sender.hasPermission("pingkick.info") ? "Max: " + config.getLong("max") + " ms\nCommand: /" + config.getString("command") + "\nPeriod: " + config.getLong("period") + " ticks" : "You aren't allowed to execute this command");
        } else if (args.length > 1) {
            switch (args[0].toLowerCase()) {
                case "max":
                    if (!sender.hasPermission("pingkick.max")) {
                        sender.sendMessage("You aren't allowed to execute this command");
                        return false;
                    }
                    try {
                        config.set("max", Long.parseLong(args[1]));
                        sender.sendMessage("Changed max ping to " + args[1] + " ms");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Please provide a valid integer");
                    }
                    break;
                case "command":
                    if (!sender.hasPermission("pingkick.command")) {
                        sender.sendMessage("You aren't allowed to execute this command");
                        return false;
                    }
                    String command = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    config.set("command", command.startsWith("/") ? command.substring(1) : command);
                    sender.sendMessage("Changed command to /" + config.getString("command"));
                    break;
                case "period":
                    if (!sender.hasPermission("pingkick.period")) {
                        sender.sendMessage("You aren't allowed to execute this command");
                        return false;
                    }
                    try {
                        config.set("period", Long.parseLong(args[1]));
                        sender.sendMessage("Changed period to " + args[1] + " ticks");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Please provide a valid integer");
                    }
                    break;
                default:
                    sender.sendMessage("Invalid usage");
            }
        } else {
            sender.sendMessage("Invalid usage");
        }
        this.saveConfig();
        return false;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completes = new ArrayList<>();
        if(args.length == 1) {
            if (sender.hasPermission("pingkick.info"))
                completes.add("info");
            if (sender.hasPermission("pingkick.max"))
                completes.add("max");
            if (sender.hasPermission("pingkick.command"))
                completes.add("command");
            if (sender.hasPermission("pingkick.period"))
                completes.add("period");
        }
        return completes;
    }
}
