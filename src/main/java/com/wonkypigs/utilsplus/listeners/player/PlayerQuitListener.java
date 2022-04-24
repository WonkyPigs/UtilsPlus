package com.wonkypigs.utilsplus.listeners.player;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerQuitListener implements Listener {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @EventHandler
    public void OnPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("join-leave-messages")) {
            if (!Objects.equals(getConfig("leave-message"), "none")) {
                event.setQuitMessage(getConfig("leave-message").toLowerCase().replace("{player}", player.getName()).replace("{displayname}", player.getDisplayName()));
            }
        }
    }
}
