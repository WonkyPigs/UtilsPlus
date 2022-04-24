package com.wonkypigs.utilsplus.listeners.player;

import com.wonkypigs.utilsplus.UtilsPlus;
import com.wonkypigs.utilsplus.mysqlSetterGetter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoinListener extends mysqlSetterGetter implements Listener{

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);
    mysqlSetterGetter mysql = new mysqlSetterGetter();

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location spawn = plugin.getConfig().getLocation("spawn");
        playerExistsInDatabase(player.getUniqueId());
        if (IsPlayerBanned(player.getUniqueId())) {
            player.kickPlayer("You are banned from this server for \n" + ChatColor.RED + GetBanReason(player.getUniqueId()));
        }
        else if (!player.hasPlayedBefore()) {
            if (spawn != null) {
                player.teleport(spawn);
            }
            else{
                player.sendMessage(getConfig("prefix") + getConfig("spawn-not-set"));
            }
            if (plugin.getConfig().getBoolean("join-leave-messages")) {
                if (!Objects.equals(getConfig("first-join-message"), "none")) {
                    event.setJoinMessage(getConfig("first-join-message").toLowerCase().replace("{player}", player.getName()).replace("{displayname}", player.getDisplayName()));
                }
            }
        }
        else if (plugin.getConfig().getBoolean("spawnonjoin")) {
            if (spawn != null) {
                player.teleport(spawn);
            }
            else{
                player.sendMessage(getConfig("prefix") + getConfig("spawn-not-set"));
            }
            if (plugin.getConfig().getBoolean("join-leave-messages")) {
                if (!Objects.equals(getConfig("join-message"), "none")) {
                    event.setJoinMessage(getConfig("join-message").toLowerCase().replace("{player}", player.getName()).replace("{displayname}", player.getDisplayName()));
                }
            }
        }
    }
}
