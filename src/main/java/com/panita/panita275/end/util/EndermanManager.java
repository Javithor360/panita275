package com.panita.panita275.end.util;

import com.panita.panita275.core.util.EntityUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EndermanManager {
    private static final Set<Enderman> trackedEndermen = new HashSet<>();
    private static int taskId = -1;

    public static void startTask(Plugin plugin) {
        if (taskId != -1) return; // Task already running

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Iterator<Enderman> iterator = trackedEndermen.iterator();

            while (iterator.hasNext()) {
                Enderman enderman = iterator.next();

                if (enderman == null || !enderman.isValid() || enderman.isDead()) {
                    iterator.remove();
                    continue;
                }

                Player nearest = getNearestPlayer(enderman.getLocation(), 32);
                if (nearest != null && nearest.getGameMode() == GameMode.SURVIVAL) {
                    enderman.setTarget(nearest);
                    enderman.setAggressive(true);

                    // If within 8 blocks, consider teleporting
                    if (enderman.getLocation().distance(nearest.getLocation()) <= 12) {
                        String afkStatus = PlaceholderAPI.setPlaceholders(nearest, "%AFKPlus_Status%");

                        boolean isAFK = !afkStatus.isEmpty();
                        boolean onGround = nearest.getLocation().clone().subtract(0, 0.1, 0)
                                .getBlock().getType().isSolid();

                        // Teleport chance
                        if (!isAFK && nearest.getVehicle() == null && onGround && Math.random() <= 0.50) {
                            Location tpLoc = nearest.getLocation().add(0, 1, 0);
                            enderman.teleport(tpLoc);
                        }
                    }
                }
            }

        }, 20L * 5, 20L * 5); // every 5 seconds
    }

    public static void stopTask() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
        trackedEndermen.clear();
    }

    public static void trackEnderman(Enderman enderman) {
        trackedEndermen.add(enderman);
    }

    private static Player getNearestPlayer(Location loc, double radius) {
        double closest = radius * radius;
        Player nearest = null;

        for (Player player : loc.getWorld().getPlayers()) {
            if (player.getGameMode() != GameMode.SURVIVAL) continue;
            if (player.isDead() || !player.isOnline()) continue;

            double dist = player.getLocation().distanceSquared(loc);
            if (dist < closest) {
                closest = dist;
                nearest = player;
            }
        }
        return nearest;
    }
}
