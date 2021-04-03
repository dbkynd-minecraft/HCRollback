package com.dbkynd.hcrollback;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class CoreProtect {
    public CoreProtectAPI getCoreProtect() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (!(plugin instanceof net.coreprotect.CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((net.coreprotect.CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 6) {
            return null;
        }

        return CoreProtect;
    }
}
