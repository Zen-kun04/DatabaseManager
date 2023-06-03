package com.donbaguette.databasemanager.inventories;

import com.cryptomorin.xseries.XMaterial;
import com.donbaguette.databasemanager.DatabaseManager;
import com.donbaguette.databasemanager.connections.MySQL;
import com.donbaguette.databasemanager.manager.ConfigManager;
import com.donbaguette.databasemanager.manager.DBManager;
import com.donbaguette.databasemanager.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableListing {


    private final FileConfiguration config;
    private final MySQL mySQL;

    public TableListing (DatabaseManager databaseManager) {
        this.config = new ConfigManager(databaseManager, "config.yml").getConfig();
        this.mySQL = new MySQL(this.config);
    }




    public Inventory createInv(String databaseName) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Table List of " + databaseName);
        try {
            Connection conn = this.mySQL.getConnection(databaseName);
            DBManager dbManager = new DBManager(conn);
            ItemCreator itemCreator = new ItemCreator();
            int index = 0;
            System.out.println("ALL TABLES => " + dbManager.getTables());
            for(String tableName : dbManager.getTables()){

                ItemStack itemStack = itemCreator.createItemWithMeta(XMaterial.ANVIL.parseMaterial(),
                        "&c" + tableName,
                        new ArrayList<>());
                inventory.setItem(index, itemStack);
                index += 2;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return inventory;
    }

}
