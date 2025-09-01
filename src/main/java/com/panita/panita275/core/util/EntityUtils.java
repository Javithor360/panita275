package com.panita.panita275.core.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class EntityUtils {
    /**
     * Get the nearest player to a location within a certain radius.
     * @param loc The location to check from
     * @param radius The radius to check within
     * @return The nearest player, or null if none found
     */
    public static Player getNearestPlayer(Location loc, double radius) {
        World world = loc.getWorld();
        if (world == null) return null;

        Player nearest = null;
        double closest = radius * radius; // Compare squared distances

        for (Player player : world.getPlayers()) {
            if (!player.getWorld().equals(world)) continue;

            double dist = player.getLocation().distanceSquared(loc);
            if (dist <= closest) {
                closest = dist;
                nearest = player;
            }
        }

        return nearest;
    }

    /**
     * Check if there's enough space in height for an entity.
     * @param loc The location to check
     * @param up Number of blocks needed above
     * @param down Number of blocks needed below
     * @return True if there's enough space, false otherwise
     */
    public static boolean isEnoughSpaceY(Location loc, int up, int down) {
        World world = loc.getWorld();
        if (world == null) return false;

        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        for (int dy = -down; dy <= up; dy++) {
            Material type = world.getBlockAt(x, y + dy, z).getType();
            if (!type.isAir()) {
                return false;
            }
        }
        return true;
    }
}
