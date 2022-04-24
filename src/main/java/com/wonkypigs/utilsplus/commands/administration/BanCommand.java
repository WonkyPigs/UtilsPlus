package com.wonkypigs.utilsplus.commands.administration;

import com.wonkypigs.utilsplus.UtilsPlus;
import com.wonkypigs.utilsplus.mysqlSetterGetter;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BanCommand extends mysqlSetterGetter implements CommandExecutor {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("utilsplus.ban")) {
            if(args.length < 2) {
                sender.sendMessage(getConfig("prefix") + "Usage: /ban <player> <reason>");
                return true;
            }
            else{
                Player player = Bukkit.getPlayer(args[0]);
                if (player == sender){
                    sender.sendMessage(getConfig("prefix") + "You cannot ban yourself!");
                    return true;
                }
                else {
                    assert player != null;
                    if (player.hasPermission("utilsplus.ban.exempt")) {
                        sender.sendMessage(getConfig("prefix") + getConfig("player-exempt") + " banned!");
                    }
                    else if (IsPlayerBanned(player.getUniqueId())){
                        sender.sendMessage(getConfig("prefix") + "That player is already banned!");
                    }
                    else {
                        StringBuilder reason = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            reason.append(args[i]).append(" ");
                        }
                        BanPlayer(player.getUniqueId(), reason.toString());
                        sender.sendMessage(getConfig("prefix") + "You have banned " + ChatColor.RED + player.getName() + ChatColor.GREEN + " for reason " + ChatColor.RED + reason);
                        Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).kickPlayer(getConfig("prefix") + "You have been permanently banned for reason " + ChatColor.RED + reason);
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
