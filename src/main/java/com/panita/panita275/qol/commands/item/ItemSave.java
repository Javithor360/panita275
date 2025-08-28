package com.panita.panita275.qol.commands.item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.core.util.CommandUtils;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@SubCommandSpec(
        parent = "panitacraft item",
        name = "save",
        description = "Save the item in your hand into the custom item collection.",
        syntax = "/panitacraft item save <item_name>",
        playerOnly = true
)
public class ItemSave implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        // Player Only is already checked by the annotation
        Player player = (Player) sender;

        String itemName = args[0];
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType().isAir()) {
            Messenger.prefixedSend(player, "&cYou need to hold an &eitem &ain your main hand.");
            return;
        }

        if (CustomItemManager.saveItem(itemName, itemInHand)) {
            Messenger.prefixedSend(player, "&aItem &e" + itemName + " &asaved successfully.");
        } else {
            Messenger.prefixedSend(player, "&cAn error occurred while saving the item.");
        }
    }
}
