package com.wonkypigs.utilsplus.commands.utility;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("utilsplus.feed")) {
                if (args.length == 0) {
                    if (sender.hasPermission("utilsplus.feed.self")) {
                        Player player = (Player) sender;
                        player.setFoodLevel(20);
                        player.setSaturation(20);
                    }
                    else {
                        sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
                        return true;
                    }
                }
                else if (args.length == 1) {
                    if (sender.hasPermission("utilsplus.feed.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setFoodLevel(20);
                            target.setSaturation(20);
                        }
                        else {
                            sender.sendMessage(getConfig("prefix") + getConfig("player-not-online"));
                            return true;
                        }
                    }
                    else {
                        sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
                        return true;
                    }
                }
                else {
                    sender.sendMessage(getConfig("prefix") + getConfig("too-many-args"));
                    return true;
                }
            }
            else {
                sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
                return true;
            }
        }
        else {
            sender.sendMessage(getConfig("prefix") + getConfig("must-be-a-player"));
        }
        return true;
    }
}
