package com.wonkypigs.utilsplus.commands.administration;

import com.wonkypigs.utilsplus.UtilsPlus;
import com.wonkypigs.utilsplus.mysqlSetterGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand extends mysqlSetterGetter implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("utilsplus.mute")) {
            if (args.length < 2) {
                sender.sendMessage(getConfig("prefix") + "Usage: /mute <player> <reason>");
                return true;
            }
            else {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == sender) {
                    sender.sendMessage(getConfig("prefix") + "You cannot mute yourself!");
                    return true;
                }
                else {
                    assert player != null;
                    if (!player.isOnline()) {
                        sender.sendMessage(getConfig("prefix") + getConfig("player-not-online"));
                        return true;
                    }
                    else if (player.hasPermission("utilsplus.exempt.mute")) {
                        sender.sendMessage(getConfig("prefix") + getConfig("player-exempt") + " muted!");
                        return true;
                    }
                    else if (IsPlayerMuted(player.getUniqueId())) {
                        sender.sendMessage(getConfig("prefix") + "That player is already muted!");
                        return true;
                    }
                    else {
                        StringBuilder reason = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            reason.append(args[i]).append(" ");
                        }
                        MutePlayer(player.getUniqueId(), reason.toString());
                        sender.sendMessage(getConfig("prefix") + "You have muted " + player.getName() + " permanently for " + reason);
                        player.sendMessage(getConfig("prefix") + "You have been muted permanently by " + sender.getName() + " for " + reason.toString());
                        return true;
                    }
                }
            }
        }
        else {
            sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
        }
        return true;
        }
    }