package com.donbaguette.databasemanager.inventories;

import com.cryptomorin.xseries.XMaterial;
import com.donbaguette.databasemanager.DatabaseManager;
import com.donbaguette.databasemanager.connections.MySQL;
import com.donbaguette.databasemanager.manager.ConfigManager;
import com.donbaguette.databasemanager.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EntityList {

    private final FileConfiguration config;
    private final MySQL mySQL;

    public EntityList (DatabaseManager databaseManager) {
        this.config = new ConfigManager(databaseManager, "config.yml").getConfig();
        this.mySQL = new MySQL(this.config);
    }

    public Inventory createInv(String database, String table, int page) {
        try{
            Connection conn = this.mySQL.getConnection(database);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM " + table);
            ResultSet resultSet = preparedStatement.executeQuery();
            Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&bData in table: &9&l" + table));
            ItemCreator itemCreator = new ItemCreator();
            List<ItemStack> items = new ArrayList<>();
            if(resultSet.next()) {
                int i = 0;
                while (resultSet.next()) {
                    ItemStack itemStack = itemCreator.createItem(Material.matchMaterial("SKULL_ITEM"));
                    itemStack.setDurability((short) 3);
                    String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JkNTg0ODlkMjg5YmZiYjdjODI5YWI0Yzg1YTNmMDVkNmM3NTYyYjhiNTEzOTBhZGJkN2JiODMwMGU0MTI4YiJ9fX0=";
                    ItemStack newItem = Bukkit.getUnsafe().modifyItemStack(itemStack, "{SkullOwner:{Id:\"" + UUID.randomUUID() + "\",Properties:{textures:[{Value:\""
                            + texture + "\"}]}}}");
                    SkullMeta meta = (SkullMeta) newItem.getItemMeta();
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b#" + i+1));
                    List<String> data = new ArrayList<>();
                    for(int x = 0; x < resultSet.getMetaData().getColumnCount(); x++) {
                        data.add(resultSet.getMetaData().getColumnName(x+1) + ": " + resultSet.getObject(x+1).toString());
                    }
                    meta.setLore(data);
                    newItem.setItemMeta(meta);
                    items.add(newItem);
//                    inventory.setItem(i, newItem);
                    i++;
                }
                if(items.size() >= 46) {
                    for(int x=0;x < 45; x++) {
                        inventory.setItem(x, items.get(x));
                    }
                    inventory.setItem(48, itemCreator.createItem(XMaterial.ARROW.parseMaterial()));
                    inventory.setItem(50, itemCreator.createItem(XMaterial.ARROW.parseMaterial()));
                }else {
                    for(int x=0;x < items.size(); x++) {
                        inventory.setItem(x, items.get(x));
                    }
                }
            }else {
                inventory.setItem(0, itemCreator.createItemWithMeta(XMaterial.PLAYER_HEAD.parseMaterial(), "No data found", new ArrayList<>()));
            }
            return inventory;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
