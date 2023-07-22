package de.tamion;

import de.tamion.commands.SetCheckFrequency;
import de.tamion.commands.SetKickCommand;
import de.tamion.commands.SetPingThreshold;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PingKick extends JavaPlugin {

    private static PingKick plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getCommand("setpingthreshold").setExecutor(new SetPingThreshold());
        getCommand("setcheckfrequency").setExecutor(new SetCheckFrequency());
        getCommand("setkickcommand").setExecutor(new SetKickCommand());

        defaultconfig();
        checkschedueler();
    }

    public static PingKick getPlugin() {
        return plugin;
    }

    public void checkschedueler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(getConfig().getInt("threshold") != 0) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(player.getPing() > getConfig().getInt("threshold")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("kickcommand").replaceAll("\\{player}", player.getName()));
                        }
                    });
                }
            }
        }, 100, getConfig().getInt("frequency"));
    }

    public void defaultconfig() {
        FileConfiguration config = getConfig();
        if(!config.contains("frequency")) {
            config.set("frequency", 100);
        }
        if(!config.contains("kickcommand")) {
            config.set("kickcommand", "kick {player} High Ping");
        }
        saveConfig();
    }
}