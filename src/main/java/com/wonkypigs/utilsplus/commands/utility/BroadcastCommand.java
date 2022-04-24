package com.wonkypigs.utilsplus.commands.utility;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("utilsplus.broadcast")) {
            if (args.length == 0) {
                sender.sendMessage(getConfig("prefix") + "Usage: /broadcast <message>");
            } else {
                String message = "";
                for (String arg : args) {
                    message = message + " " + arg;
                }
                message = message.substring(1);
                sender.getServer().broadcastMessage(getConfig("broadcast-prefix") + message);
            }
        } else {
            sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
        }
        return true;
    }
}