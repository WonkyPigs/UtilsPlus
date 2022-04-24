package com.wonkypigs.utilsplus.commands.utility;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("utilsplus.invsee")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == sender){
                        sender.sendMessage(getConfig("prefix") + "You cannot spy your own inventory!");
                        return true;
                    }
                    else if (target == null){
                        sender.sendMessage(getConfig("prefix") + getConfig("player-not-online"));
                        return true;
                    }
                    else {
                        Player player = (Player) sender;
                        player.openInventory(target.getInventory());
                        return true;
                    }
                }
                else {
                    sender.sendMessage(getConfig("prefix") + "Usage: /invsee <player>");
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
