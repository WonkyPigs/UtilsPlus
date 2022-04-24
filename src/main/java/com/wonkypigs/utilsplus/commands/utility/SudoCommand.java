package com.wonkypigs.utilsplus.commands.utility;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("utilsplus.sudo")) {
            if (args.length < 2) {
                sender.sendMessage(getConfig("prefix") + "/sudo <player> <command>");
            }
            else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(getConfig("prefix") + getConfig("player-not-online"));
                    return true;
                }
                else if (target.hasPermission("utilsplus.sudo.exempt")) {
                    sender.sendMessage(getConfig("prefix") + getConfig("player-exempt") + " sudo-ed!");
                }
                else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }
                    String commandToRun = sb.toString().trim();
                    target.performCommand(commandToRun);
                }
            }
        }
        else {
            sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
        }
        return true;
    }
}
