package com.donbaguette.databasemanager.inventories;

import com.cryptomorin.xseries.XMaterial;
import com.donbaguette.databasemanager.manager.DBManager;
import com.donbaguette.databasemanager.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBListing {
    private final DBManager dbManager;

    public DBListing(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    public Inventory createInv() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Database List");
        try {
            ItemCreator itemCreator = new ItemCreator();
            int index = 0;
            for(String databaseName : dbManager.getDatabases()){

                ItemStack itemStack = itemCreator.createItemWithMeta(XMaterial.GRASS_BLOCK.parseMaterial(),
                        "&6" + databaseName,
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
