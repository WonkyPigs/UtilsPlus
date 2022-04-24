package com.wonkypigs.utilsplus.commands.utility;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("utilsplus.spawn")) {
            if (sender instanceof Player) {
                Location spawn = plugin.getConfig().getLocation("spawn");
                Player player = (Player) sender;
                if (spawn != null) {
                    player.teleport(spawn);
                    player.sendMessage(getConfig("prefix") + getConfig("spawn-teleport"));
                    return true;
                }
                else {
                    player.sendMessage(getConfig("prefix") + getConfig("spawn-not-set"));
                    return true;
                }
            }
            else{
                sender.sendMessage(getConfig("prefix") + getConfig("must-be-a-player"));
                return true;
            }
        }
        else{
            sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
        }
        return true;
    }
}

