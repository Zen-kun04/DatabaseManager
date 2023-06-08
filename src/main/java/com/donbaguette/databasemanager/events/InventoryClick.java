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
import org.bukkit.inventory.ItemStack;

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
        Player player = (Player) event.getWhoClicked();
        if(inventoryTitle.equals("Database List")) {
            player.closeInventory();
            String databaseName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            player.openInventory(tableListing.createInv(databaseName));
            event.setCancelled(true);
        }else if(inventoryTitle.startsWith("Table List of ")) {
            player.closeInventory();
            String tableName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            player.openInventory(this.tableOptions.createInv(tableName));
            event.setCancelled(true);
        }else if(ChatColor.stripColor(inventoryTitle).startsWith("Settings for table:")) {
            int slot = event.getSlot();
            switch (slot) {
                case 0:
                    // show entities
                    player.closeInventory();

                    break;
                case 2:
                    // update entity
                    break;
                case 4:
                    // Delete entity
                    break;
                case 6:
                    // Get entity by
                    break;
                case 8:
                    // create entity
                    break;
            }
            event.setCancelled(true);
        }
    }

}
