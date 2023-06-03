package com.donbaguette.databasemanager;

import com.donbaguette.databasemanager.inventories.DBListing;
import com.donbaguette.databasemanager.manager.DBManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private final DBManager dbManager;

    public Commands(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(args.length > 0) {

            }else{
                DBListing dbListing = new DBListing(this.dbManager);
                player.openInventory(dbListing.createInv());
            }
        }
        return false;
    }
}
