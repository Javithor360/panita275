package com.panita.panita275.qol.commands;

import com.panita.panita275.Panitacraft;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.dynamic.TabSuggestingCommand;
import com.panita.panita275.core.commands.identifiers.CommandMeta;
import com.panita.panita275.core.commands.identifiers.CommandSpec;
import com.panita.panita275.core.util.SoundUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.stream.Collectors;

@CommandSpec(
        name = "sharecoordinates",
        description = "Share your coordinates with other players.",
        syntax = "/sharecoordinates [player]",
        aliases = {"c", "coords"},
        playerOnly = true
)
public class ShareCoordinates implements AdvancedCommand, TabSuggestingCommand {
    private boolean playSoundEnabled() {
        return Panitacraft.getConfigManager().getBoolean("quality-of-life.sharecoordinates.playSound", false);
    }

    private void play(Player player, String path, String fallback) {
        if (!playSoundEnabled()) return;
        String key = Panitacraft.getConfigManager().getString(path, fallback);
        SoundUtils.play(player, key, 1.0f, 1.0f);
    }

    private void broadcastSound(String path, String fallback, Player source) {
        if (!playSoundEnabled()) return;
        String key = Panitacraft.getConfigManager().getString(path, fallback);
        source.getWorld().getPlayers().forEach(p -> SoundUtils.play(p, key, 1.0f, 1.0f));
    }

    private void send(Player target, String path, String def, Player context) {
        String msg = Panitacraft.getConfigManager().getString(path, def);
        Messenger.prefixedSend(target, PlaceholderAPI.setPlaceholders(context, msg));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (Panitacraft.getConfigManager().getBoolean("quality-of-life.sharecoordinates.enabled", true)) {
            if (!(sender instanceof Player player)) {
                Messenger.consoleSend(sender, "&cEste comando solo puede ser ejecutado por un jugador.");
                return;
            }

            if (args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null || !target.isOnline()) {
                    Messenger.prefixedSend(player, "&cEl jugador " + args[0] + " no está en línea.");
                    return;
                } else if (target == player) {
                    send(player, "quality-of-life.sharecoordinates.selfMessage",
                            "&7Tu ubicación es &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)", player);
                    play(player, "quality-of-life.sharecoordinates.soundPrivate", "minecraft:block.chest.open");
                    return;
                }

                send(target, "quality-of-life.sharecoordinates.privateMessage",
                        "&b%player_name% &7te ha compartido su ubicación, &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)", player);
                send(player, "quality-of-life.sharecoordinates.sentPrivateConfirmation",
                        "&7Has compartido tu ubicación con &b%player_name%&7.", target);

                play(target, "quality-of-life.sharecoordinates.soundPrivate", "minecraft:block.chest.open");
                play(player, "quality-of-life.sharecoordinates.soundPrivate", "minecraft:block.chest.open");
                return;
            }

            String msg = Panitacraft.getConfigManager().getString(
                    "quality-of-life.sharecoordinates.publicMessage",
                    "&b%player_name% &7se encuentra en &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)"
            );

            Messenger.prefixedBroadcast(PlaceholderAPI.setPlaceholders(player, msg));
            broadcastSound("quality-of-life.sharecoordinates.soundPublic", "minecraft:entity.player.levelup", player);
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
