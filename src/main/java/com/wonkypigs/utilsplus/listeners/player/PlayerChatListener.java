package com.wonkypigs.utilsplus.listeners.player;

import com.wonkypigs.utilsplus.UtilsPlus;
import com.wonkypigs.utilsplus.mysqlSetterGetter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class PlayerChatListener extends mysqlSetterGetter implements Listener {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (IsPlayerMuted(player.getUniqueId())) {
            player.sendMessage(getConfig("prefix") + getConfig("speak-on-mute"));
            event.setCancelled(true);
        }
        else if (plugin.getConfig().getBoolean("format-chat")) {
            if (!Objects.equals(getConfig("chat-format"), "")) {
                event.setFormat(getConfig("chat-format")
                        .replace("{displayname}", player.getDisplayName())
                        .replace("{message}", event.getMessage())
                        .replace("{prefix}", getConfig("prefix")));
            }
        }
    }
}
