package com.wonkypigs.utilsplus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import com.wonkypigs.utilsplus.commands.administration.*;
import com.wonkypigs.utilsplus.commands.utility.*;
import com.wonkypigs.utilsplus.listeners.player.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class UtilsPlus extends JavaPlugin {
    private Connection connection;
    public String host, database, username, password, table;
    public int port;

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerListeners();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        mySqlSetup();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(getCommand("broadcast")).setExecutor(new BroadcastCommand());
        Objects.requireNonNull(getCommand("ban")).setExecutor(new BanCommand());
        Objects.requireNonNull(getCommand("unban")).setExecutor(new UnBanCommand());
        Objects.requireNonNull(getCommand("mute")).setExecutor(new MuteCommand());
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new UnMuteCommand());
        Objects.requireNonNull(getCommand("warn")).setExecutor(new WarnCommand());
        Objects.requireNonNull(getCommand("kick")).setExecutor(new KickCommand());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyCommand());
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new InvseeCommand());
        Objects.requireNonNull(getCommand("sudo")).setExecutor(new SudoCommand());
        Objects.requireNonNull(getCommand("heal")).setExecutor(new HealCommand());
        Objects.requireNonNull(getCommand("feed")).setExecutor(new FeedCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
    }

    public void mySqlSetup() {
        host = getConfig().getString("database.host");
        port = getConfig().getInt("database.port");
        database = getConfig().getString("database.database");
        username = getConfig().getString("database.username");
        password = getConfig().getString("database.password");
        table = getConfig().getString("database.table");

        try{
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password));

                Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + getConfig().getString("mysql-connection-success"));

                getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (UUID varchar(200), NAME varchar(200), ECO int, MUTED boolean, BANNED boolean, WARNINGS int, MUTE_REASON varchar(200), BAN_REASON varchar(200))").executeUpdate();
            }
        }catch(SQLException | ClassNotFoundException e){
            Bukkit.getConsoleSender().sendMessage(getConfig().getString("prefix") + getConfig().getString("unexpected-error"));
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(getConfig().getString("prefix") + "UtilsPlus has been disabled!");
    }
}
