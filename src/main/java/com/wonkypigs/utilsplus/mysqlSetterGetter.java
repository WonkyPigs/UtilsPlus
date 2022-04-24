package com.wonkypigs.utilsplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class mysqlSetterGetter {

    UtilsPlus plugin = UtilsPlus.getPlugin(UtilsPlus.class);

    public String getConfig(String key) {
        String ans = plugin.getConfig().getString(key);
        assert ans != null;
        return ChatColor.translateAlternateColorCodes('&', ans);
    }

    public boolean playerExistsInDatabase(UUID uuid) {
        try {
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            return results.next();
        }catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayerInDatabase(final UUID uuid, Player player) {
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            results.next();
            if(!playerExistsInDatabase(uuid)) {
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO " + plugin.table + " (UUID, NAME, ECO, MUTED, BANNED, WARNINGS, MUTE_REASON, BAN_REASON) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.setBoolean(4, false);
                insert.setBoolean(5, false);
                insert.setInt(6, 0);
                insert.setString(7, "");
                insert.setString(8, "");
                insert.executeUpdate();
            }
        }catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
    }

    public boolean IsPlayerBanned(final UUID uuid) {
        try{
            createPlayerInDatabase(uuid, Bukkit.getPlayer(uuid));
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT BANNED FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            results.next();
            return results.getBoolean("BANNED");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
        return false;
    }

    public String GetBanReason(final UUID uuid) {
        try {
            createPlayerInDatabase(uuid, Bukkit.getPlayer(uuid));
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT BAN_REASON FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            results.next();
            return results.getString("BAN_REASON");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
        return null;
    }

    public boolean IsPlayerMuted(final UUID uuid) {
        try{
            createPlayerInDatabase(uuid, Bukkit.getPlayer(uuid));
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT MUTED FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            results.next();
            return results.getBoolean("MUTED");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
        return false;
    }

    public String GetMuteReason(final UUID uuid) {
        try {
            createPlayerInDatabase(uuid, Bukkit.getPlayer(uuid));
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT MUTE_REASON FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            results.next();
            return results.getString("MUTE_REASON");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
        return null;
    }

    public void MutePlayer(final UUID uuid, String reason) {
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET MUTED=?, MUTE_REASON=? WHERE UUID=?");
            statement.setBoolean(1, true);
            statement.setString(2, reason);
            statement.setString(3, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
    }

    public void UnMutePlayer(final UUID uuid) {
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET MUTED=?, MUTE_REASON=? WHERE UUID=?");
            statement.setBoolean(1, false);
            statement.setString(2, "");
            statement.setString(3, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
    }

    public void BanPlayer(final UUID uuid, String reason) {
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET BANNED=?, BAN_REASON=? WHERE UUID=?");
            statement.setBoolean(1, true);
            statement.setString(2, reason);
            statement.setString(3, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
    }

    public void UnBanplayer(final UUID uuid) {
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET BANNED=?, BAN_REASON=? WHERE UUID=?");
            statement.setBoolean(1, false);
            statement.setString(2, "");
            statement.setString(3, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
    }

    public void WarnPlayer(final UUID uuid) {
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table + " SET WARNINGS=WARNINGS+1 WHERE UUID=?");
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(getConfig("prefix") + getConfig("unexpected-error"));
            e.printStackTrace();
        }
    }
}
