package com.dbkynd.hcrollback;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class UserDataHandler {
    private final HCRollback plugin;

    public UserDataHandler(HCRollback plugin) {
        this.plugin = plugin;
    }

    public void saveRollbackTime(UUID uuid, int currentTime) {
        File userFile = new File(plugin.getDataFolder(), uuid + ".yml");
        try {
            FileConfiguration userConfig = YamlConfiguration.loadConfiguration(userFile);
            userConfig.set("lastRollback", currentTime);
            userConfig.save(userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLastRollbackTime(UUID uuid) {
        File userFile = new File(plugin.getDataFolder(), uuid + ".yml");
        try {
            FileConfiguration userConfig = YamlConfiguration.loadConfiguration(userFile);
            return userConfig.getInt("lastRollback");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
