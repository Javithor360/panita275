package com.panita.panita275.core.chat;

import com.panita.panita275.core.util.Global;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messenger {
    private static final MiniMessage mini = MiniMessage.miniMessage();
    private static BukkitAudiences audiences;

    /**
     * Initializes the Messenger class with the BukkitAudiences instance.
     *
     * @param adventure The BukkitAudiences instance to use for sending messages.
     */
    public static void init(BukkitAudiences adventure) {
        audiences = adventure;
    }

    // ----> Basic Mini <----

    /**
     * Converts a raw message to a MiniMessage component.
     *
     * @param msg The raw message to convert.
     * @return The MiniMessage component.
     */
    public static Component mini(String msg) {
        if (msg == null) return Component.empty();
        String converted = LegacyToMiniConverter.convert(msg);
        return MiniMessage.miniMessage().deserialize(converted);
    }

    /**
     * Converts a raw message to a MiniMessage component with the plugin prefix.
     *
     * @param msg The raw message to convert.
     * @return The MiniMessage component with the plugin prefix.
     */
    public static Component miniPrefixed(String msg) {
        String prefixMini = LegacyToMiniConverter.convert(Global.RAW_PREFIX);
        String bodyMini = LegacyToMiniConverter.convert(msg);
        String combined = prefixMini + bodyMini;

        return MiniMessage.miniMessage().deserialize(combined);
    }

    /**
     * Applies PlaceholderAPI placeholders to a message for a specific player.
     *
     * @param player The player to apply placeholders for.
     * @param msg The raw message with placeholders.
     * @return The message with placeholders applied.
     */
    private static String applyPlaceholders(Player player, String msg) {
        if (msg == null) return "";
        return PlaceholderAPI.setPlaceholders(player, msg);
    }

    // ----> Senders <----

    /**
     * Sends a message to a command sender.
     *
     * @param sender The command sender to send the message to.
     * @param msg The raw message to send.
     */
    public static void send(CommandSender sender, String msg) {
        audiences.sender(sender).sendMessage(mini(msg));
    }

    /**
     * Sends a message to a command sender with the plugin prefix.
     *
     * @param sender The command sender to send the message to.
     * @param msg The raw message to send.
     */
    public static void prefixedSend(CommandSender sender, String msg) {
        audiences.sender(sender).sendMessage(miniPrefixed(msg));
    }

    /**
     * Sends a message to a player.
     *
     * @param player The player to send the message to.
     * @param raw The raw message to send.
     */
    public static void send(Player player, String raw) {
        audiences.player(player).sendMessage(mini(raw));
    }

    /**
     * Sends a message to a player with the plugin prefix.
     *
     * @param player The player to send the message to.
     * @param raw The raw message to send.
     */
    public static void prefixedSend(Player player, String raw) {
        audiences.player(player).sendMessage(miniPrefixed(raw));
    }

    /**
     * Sends a message to a player applying PlaceholderAPI placeholders for a specific context player.
     *
     * @param receiver The player to send the message to.
     * @param context The player to apply placeholders for.
     * @param raw The raw message to send.
     */
    public static void placeholderSend(Player receiver, Player context, String raw) {
        String parsed = applyPlaceholders(context, raw);
        audiences.player(receiver).sendMessage(mini(parsed));
    }

    /**
     * Sends a message to a player applying PlaceholderAPI placeholders for themselves.
     *
     * @param receiver The player to send the message to.
     * @param raw The raw message to send.
     */
    public static void placeholderSend(Player receiver, String raw) {
        placeholderSend(receiver, receiver, raw);
    }

    /**
     * Sends a message to a player with the plugin prefix and applies PlaceholderAPI placeholders for a specific context player.
     *
     * @param receiver The player to send the message to.
     * @param context The player to apply placeholders for.
     * @param raw The raw message to send.
     */
    public static void prefixedPlaceholderSend(Player receiver, Player context, String raw) {
        String parsed = applyPlaceholders(context, raw);
        audiences.player(receiver).sendMessage(miniPrefixed(parsed));
    }

    /**
     * Sends a message to a player applying PlaceholderAPI placeholders for themselves.
     *
     * @param receiver The player to send the message to.
     * @param raw The raw message to send.
     */
    public static void prefixedPlaceholderSend(Player receiver, String raw) {
        prefixedPlaceholderSend(receiver, receiver, raw);
    }

    // ----> Broadcast <----
    /**
     * Broadcasts a message to all players.
     *
     * @param msg The raw message to send.
     */
    public static void broadcast(String msg) {
        Component message = mini(msg);
        audiences.all().sendMessage(message);
    }

    /**
     * Broadcasts a message to all players with the plugin prefix.
     *
     * @param msg The raw message to send.
     */
    public static void prefixedBroadcast(String msg) {
        Component message = miniPrefixed(msg);
        audiences.all().sendMessage(message);
    }

    /**
     * Broadcasts a message to all players and applies PlaceholderAPI placeholders for a specific player.
     *
     * @param context The player to apply placeholders for.
     * @param raw The raw message to send.
     */
    public static void placeholderBroadcast(Player context, String raw) {
        String parsed = applyPlaceholders(context, raw);
        audiences.all().sendMessage(mini(parsed));
    }

    /**
     * Broadcasts a message to all players with the plugin prefix and applies PlaceholderAPI placeholders for a specific player.
     *
     * @param context The player to apply placeholders for.
     * @param raw The raw message to send.
     */
    public static void prefixedPlaceholderBroadcast(Player context, String raw) {
        String parsed = applyPlaceholders(context, raw);
        audiences.all().sendMessage(miniPrefixed(parsed));
    }

    // ---> Extra <----

    /**
     * Sends a message to the console if the sender is not a player.
     *
     * @param sender The command sender (should be a console).
     * @param msg The raw message to send.
     */
    public static void consoleSend(CommandSender sender, String msg) {
        if (!(sender instanceof Player)) {
            send(sender, msg);
        }
    }
}
