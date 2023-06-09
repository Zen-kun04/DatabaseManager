package com.donbaguette.databasemanager;

import com.donbaguette.databasemanager.connections.MySQL;
import com.donbaguette.databasemanager.events.InventoryClick;
import com.donbaguette.databasemanager.manager.ConfigManager;
import com.donbaguette.databasemanager.manager.DBManager;
import com.donbaguette.databasemanager.manager.Database;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public final class DatabaseManager extends JavaPlugin {

    private final HashMap<Player, Database> editor = new HashMap<>();

    public HashMap<Player, Database> getEditor() {
        return this.editor;
    }
    DBManager dbManager;
    Connection connection;
    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager config = new ConfigManager(this, "config.yml");
        config.createConfig();
        MySQL mySQL = new MySQL(config.getConfig());

        try {
            connection = mySQL.getConnection("");
            dbManager = new DBManager(connection);
            System.out.println(dbManager.getDatabases());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        registerEvents();
        registerCommands();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void registerCommands() {
        this.getCommand("databasemanager").setExecutor(new Commands(dbManager));
    }
    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
    }
}
