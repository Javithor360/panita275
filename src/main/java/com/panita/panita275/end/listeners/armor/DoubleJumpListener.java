package com.panita.panita275.end.listeners.armor;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.end.util.ArmorUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DoubleJumpListener implements Listener {
    private final Set<UUID> canDoubleJump = new HashSet<>();

    @EventHandler
    public void onJumpInput(PlayerInputEvent event) {
        Player player = event.getPlayer();
        UUID id = player.getUniqueId();

        // Check if the player is wearing the full Dragon Slayer armor set
        if (!ArmorUtils.hasFullDragonSlayerSet(player)) return;

        // If the player is on the ground, reset their ability to double jump
        if (player.isOnGround()) {
            canDoubleJump.add(id);
            return;
        }

        // If the player is in the air and presses jump, perform the double jump
        if (event.getInput().isJump() && canDoubleJump.contains(id)) {
            Vector velocity = player.getLocation().getDirection().multiply(0.6).setY(1); // Define jump velocity
            player.setVelocity(velocity); // Apply the velocity to the player

            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f);
            spawnDoubleJumpParticles(player); // Spawn particles for visual effect

            canDoubleJump.remove(id); // Remove the ability to double jump until they land again
        }
    }

    /**
     * Helper method to spawn particles when the player double jumps in a circle effect.
     * @param player The player who performed the double jump.
     */
    private void spawnDoubleJumpParticles(Player player) {
        // Get the center location slightly above the player
        Location center = player.getLocation().add(0, 0.8, 0);
        World world = player.getWorld();
        // Define the particle options
        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(166, 26, 252), 1.2f);
        int points = 20; // Number of points in each ring
        int rings = 3; // Number of rings

        // Spawn particles in a circular pattern
        for (int r = 0; r < rings; r++) {
            double radius = 0.4 + r * 0.5; // Increase radius for each ring
            for (int i = 0; i < points; i++) {
                double angle = 2 * Math.PI * i / points; // Angle for each point
                double x = Math.cos(angle) * radius; // X coordinate
                double z = Math.sin(angle) * radius; // Z coordinate
                Location pt = center.clone().add(x, 0, z); // Point location
                world.spawnParticle(Particle.DUST, pt, 1, 0, 0, 0, 0, dust); // Spawn particle
            }
        }
    }
}
