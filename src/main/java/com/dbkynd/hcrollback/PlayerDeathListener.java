package com.dbkynd.hcrollback;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class PlayerDeathListener implements Listener {
    final HCRollback plugin;
    UserDataHandler userDataHandler;

    public PlayerDeathListener(HCRollback plugin) {
        this.plugin = plugin;
        userDataHandler = new UserDataHandler(plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String name = player.getName();
        CoreProtectAPI api = new CoreProtect().getCoreProtect();

        event.getDrops().clear();

        int lastRollbackTime = userDataHandler.getLastRollbackTime(player.getUniqueId());
        Instant instant = Instant.now();
        long currentTime = instant.getEpochSecond();
        long playLength;
        if (lastRollbackTime > 0) {
            playLength = currentTime - lastRollbackTime;
        } else {
            playLength = currentTime - player.getFirstPlayed() / 1000;
        }
        int rollBackTime = (int) playLength;

        new BukkitRunnable() {
            @Override
            public void run() {
                HCRollback.broadcast(name + ChatColor.RED + " died!" + ChatColor.RESET + " Rolling back progress...");
                Instant instant = Instant.now();
                long startTime = instant.getEpochSecond();
                List<String[]> rollback = api.performRollback(rollBackTime, Collections.singletonList(name), null, null, null, null, 0, null);
                int blocks = 0;
                for (String[] i : rollback) {
                    CoreProtectAPI.ParseResult result = api.parseResult(i);
                    int action = result.getActionId();
                    if (action == 0) blocks++;
                    if (action == 1) blocks++;
                }
                instant = Instant.now();
                long endTime = instant.getEpochSecond();
                int duration = (int) (endTime - startTime);

                HCRollback.broadcast("Reverted " + ChatColor.GREEN + blocks + ChatColor.RESET + " block actions in " + duration + " seconds.");
                HCRollback.broadcast(name + "'s rollback is complete.");
                userDataHandler.saveRollbackTime(player.getUniqueId(), (int) currentTime);
            }
        }.runTaskAsynchronously(plugin);
    }
}
