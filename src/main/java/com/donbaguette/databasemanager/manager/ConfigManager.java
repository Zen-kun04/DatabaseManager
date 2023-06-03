package com.donbaguette.databasemanager.manager;

import com.donbaguette.databasemanager.DatabaseManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigManager extends YamlConfiguration {

    private File file;
    private FileConfiguration config;
    private final DatabaseManager databaseManager;
    private final String name;

    public ConfigManager(DatabaseManager databaseManager, String name) {
        this.databaseManager = databaseManager;
        this.name = name;
    }

    public void createConfig() {
        file = new File(this.databaseManager.getDataFolder(), name);
        if(!this.databaseManager.getDataFolder().exists()){
            this.databaseManager.getDataFolder().mkdirs();
        }
        if(!file.exists()){
            this.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(file);

    }

    public FileConfiguration getConfig(){
        if(this.config == null) {
            createConfig();
        }
        return this.config;
    }

    public void saveDefaultConfig() {
        this.databaseManager.saveResource(this.file.getName(), false);
    }

    public void saveConfig() {
        try{
            this.config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
