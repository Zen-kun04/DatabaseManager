package com.donbaguette.databasemanager.events;

import com.donbaguette.databasemanager.DatabaseManager;
import com.donbaguette.databasemanager.inventories.TableListing;
import com.donbaguette.databasemanager.inventories.TableOptions;
import com.donbaguette.databasemanager.manager.DBManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    private final DatabaseManager databaseManager;
    private final TableListing tableListing;
    private final TableOptions tableOptions;

    public InventoryClick(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.tableListing = new TableListing(this.databaseManager);
        this.tableOptions = new TableOptions(this.databaseManager);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String inventoryTitle = event.getView().getTitle();
        if(inventoryTitle.equals("Database List")) {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();
            String databaseName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            player.openInventory(tableListing.createInv(databaseName));
            event.setCancelled(true);
        }else if(inventoryTitle.startsWith("Table List of ")) {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();
            String tableName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            player.openInventory(this.tableOptions.createInv(tableName));
            event.setCancelled(true);
        }
    }

}
