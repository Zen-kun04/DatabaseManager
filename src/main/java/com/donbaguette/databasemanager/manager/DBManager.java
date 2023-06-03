package com.donbaguette.databasemanager.manager;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private final Connection connection;


    public DBManager(Connection connection) {
        this.connection = connection;
    }

    public List<String> getDatabases() throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES;");
        List<String> res = new ArrayList<>();
        while (resultSet.next()) {
            res.add(resultSet.getString(1));
        }

        return res;
    }
    public List<String> getTables() throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES;");
        List<String> res = new ArrayList<>();
        while (resultSet.next()) {
            res.add(resultSet.getString(1));
        }

        return res;
    }

}
