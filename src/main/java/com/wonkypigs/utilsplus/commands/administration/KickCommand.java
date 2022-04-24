package com.wonkypigs.utilsplus.commands.administration;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("utilsplus.kick")) {
            if (args.length < 1) {
                sender.sendMessage(getConfig("prefix") + "Usage: /kick <player> <reason>");
                return true;
            }
            else {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(getConfig("prefix") + getConfig("player-not-online"));
                    return true;
                }
                else if (player == sender) {
                    sender.sendMessage(getConfig("prefix") + "You cannot kick yourself!");
                    return true;
                }
                else if (!player.isOnline()) {
                    sender.sendMessage(getConfig("prefix") + getConfig("player-not-online"));
                    return true;
                }
                else if (player.hasPermission("utilsplus.exempt.kick")) {
                    sender.sendMessage(getConfig("prefix") + getConfig("player-exempt") + " kicked!");
                    return true;
                }
                else {
                    StringBuilder reason = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        reason.append(args[i]).append(" ");
                    }
                    player.kickPlayer(getConfig("prefix") + "You have been kicked for " + ChatColor.RED + reason.toString());
                    sender.sendMessage(getConfig("prefix") + "You have kicked " + player.getName() + " for " + reason.toString());
                }
            }
        }
        else {
            sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
        }
        return true;
    }

}
