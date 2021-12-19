package com.dbkynd.hcrollback;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class HCRollback extends JavaPlugin {
    @Override
    public void onEnable() {
        CoreProtectAPI api = new CoreProtect().getCoreProtect();
        if (api == null) {
            Bukkit.getLogger().severe("Missing CoreProtect Plugin or incorrect version. Disabling HCRollback.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    public static void broadcast(String message) {
        Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "[HC Rollback] " + ChatColor.RESET + message);
    }
}
