package com.donbaguette.databasemanager.inventories;

import com.cryptomorin.xseries.XMaterial;
import com.donbaguette.databasemanager.DatabaseManager;
import com.donbaguette.databasemanager.connections.MySQL;
import com.donbaguette.databasemanager.manager.ConfigManager;
import com.donbaguette.databasemanager.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class TableOptions {

    private final FileConfiguration config;
    private final MySQL mySQL;

    public TableOptions (DatabaseManager databaseManager) {
        this.config = new ConfigManager(databaseManager, "config.yml").getConfig();
        this.mySQL = new MySQL(this.config);
    }

    public Inventory createInv(String tableName) {
        ItemCreator itemCreator = new ItemCreator();
        Inventory inventory = Bukkit.createInventory(null, 27, "&bSettings for table: &a" + tableName);
        inventory.setItem(0, itemCreator.createItemWithMeta(XMaterial.EXPERIENCE_BOTTLE.parseMaterial(), "&eShow data", new ArrayList<>()));
        inventory.setItem(2, itemCreator.createItemWithMeta(XMaterial.DIAMOND_AXE.parseMaterial(), "&eBulk delete", new ArrayList<>()));
        inventory.setItem(4, itemCreator.createItemWithMeta(XMaterial.IRON_HOE.parseMaterial(), "&eDelete entity", new ArrayList<>()));
        inventory.setItem(6, itemCreator.createItemWithMeta(XMaterial.LEAD.parseMaterial(), "&eGet data by", new ArrayList<>()));
        return inventory;
    }

}
