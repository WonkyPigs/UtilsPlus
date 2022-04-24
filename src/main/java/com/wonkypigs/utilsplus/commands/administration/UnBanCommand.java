package com.wonkypigs.utilsplus.commands.administration;

import com.wonkypigs.utilsplus.UtilsPlus;
import com.wonkypigs.utilsplus.mysqlSetterGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnBanCommand extends mysqlSetterGetter implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("utilsplus.unban")) {
            if(args.length != 1) {
                sender.sendMessage(getConfig("prefix") + "Usage: /unban <player>");
                return true;
            }
            else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (player == sender) {
                    sender.sendMessage(getConfig("prefix") + "You cannot unban yourself!");
                    return true;
                }
                else if (!IsPlayerBanned(player.getUniqueId())) {
                    sender.sendMessage(getConfig("prefix") + "That player is not banned!");
                    return true;
                }
                else {
                    UnBanplayer(player.getUniqueId());
                    sender.sendMessage(getConfig("prefix") + "You have unbanned " + player.getName() + "!");
                }
            }
        }
        else {
            sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
            return true;
        }
        return true;
    }
}
