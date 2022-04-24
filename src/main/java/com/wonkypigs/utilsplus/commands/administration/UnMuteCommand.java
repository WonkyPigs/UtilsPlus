package com.wonkypigs.utilsplus.commands.administration;

import com.wonkypigs.utilsplus.UtilsPlus;
import com.wonkypigs.utilsplus.mysqlSetterGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnMuteCommand extends mysqlSetterGetter implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        assert ans != null;
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("utilsplus.unmute")) {
            if (args.length < 1) {
                sender.sendMessage(getConfig("prefix") + "Usage: /unmute <player>");
            }
            else {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == sender) {
                    sender.sendMessage(getConfig("prefix") + "You cannot unmute yourself!");
                }
                else {
                    assert player != null;
                    if (!IsPlayerMuted(player.getUniqueId())) {
                        sender.sendMessage(getConfig("prefix") + "That player is not muted!");
                    }
                    else {
                        UnMutePlayer(player.getUniqueId());
                        sender.sendMessage(getConfig("prefix") + "You have unmuted " + player.getName() + "!");
                        player.sendMessage(getConfig("prefix") + "You have been unmuted!");
                    }
                }
            }
            return true;
        }
        else {
            sender.sendMessage(getConfig("prefix") + getConfig("no-permission"));
        }
        return true;
    }
}

