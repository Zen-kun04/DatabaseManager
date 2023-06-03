package com.donbaguette.databasemanager.connections;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private final FileConfiguration config;

    public MySQL(FileConfiguration config) {
        this.config = config;
    }
    public Connection getConnection(String database) throws SQLException {
        String host = config.getString("connection.mysql.host");
        int port = config.getInt("connection.mysql.port");
        String user = config.getString("connection.mysql.user");
        String password = config.getString("connection.mysql.password");
        String url = "jdbc:mysql://" + host + ':' + port + '/' + database + "?characterEncoding=latin1&useConfigs=maxPerformance";
        return DriverManager.getConnection(url, user, password);
    }

}
