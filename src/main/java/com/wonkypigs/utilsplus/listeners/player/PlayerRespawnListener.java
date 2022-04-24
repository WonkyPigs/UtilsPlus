package com.wonkypigs.utilsplus.listeners.player;

import com.wonkypigs.utilsplus.UtilsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @EventHandler
    public void OnPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location spawn = plugin.getConfig().getLocation("spawn");
        if (plugin.getConfig().getBoolean("spawnondeath")) {
            if (spawn != null) {
                player.teleport(spawn);
            }else{
                player.sendMessage(getConfig("prefix") + getConfig("spawn-not-set"));
            }
        }
    }
}
