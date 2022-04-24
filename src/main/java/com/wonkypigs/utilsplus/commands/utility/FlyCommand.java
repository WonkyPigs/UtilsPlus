package com.wonkypigs.utilsplus.commands.utility;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("utilsplus.fly")) {
                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        player.sendMessage(getConfig("prefix") + getConfig("fly-disabled"));
                        return true;
                    }
                    else {
                        player.setAllowFlight(true);
                        player.sendMessage(getConfig("prefix") + getConfig("fly-enabled"));
                        return true;
                    }
                }
                else {
                    player.sendMessage(getConfig("prefix") + getConfig("no-permission"));
                    return true;
                }
            }
            else if (args.length == 1) {
                if (player.hasPermission("utilsplus.fly.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(getConfig("prefix") + getConfig("player-not-online"));
                        return true;
                    }
                    else {
                        if (target.getAllowFlight()) {
                            target.setAllowFlight(false);
                            target.sendMessage(getConfig("prefix") + getConfig("fly-disabled"));
                            String pm = getConfig("fly-disabled-other").replace("{player}", target.getName());
                            player.sendMessage(getConfig("prefix") + pm);
                            return true;
                        }
                        else {
                            target.setAllowFlight(true);
                            target.sendMessage(getConfig("prefix") + getConfig("fly-enabled"));
                            String pm = getConfig("fly-enabled-other").replace("{player}", target.getName());
                            player.sendMessage(getConfig("prefix") + pm);
                            return true;
                        }
                    }
                }
                else {
                    player.sendMessage(getConfig("prefix") + getConfig("no-permission"));
                    return true;
                }
            }
            else {
                player.sendMessage(getConfig("prefix") + "Usage: /fly [<player>]");
                return true;
            }
        }
        else{
            sender.sendMessage(getConfig("prefix") + getConfig("must-be-a-player"));
        }
        return true;
    }
}
