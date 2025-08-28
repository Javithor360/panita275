package com.panita.panita275.qol.commands.item;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.core.util.CommandUtils;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SubCommandSpec(
        parent = "panitacraft item",
        name = "tag",
        description = "Adds custom plugin metadata to the item in your hand without saving it.",
        syntax = "/panitacraft item tag <identifier>",
        playerOnly = true
)
public class ItemTag implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        Player player = (Player) sender;
        String tagName = args[0];
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            Messenger.prefixedSend(player, "&cYou must hold an item to tag it.");
            return;
        }

        ItemStack taggedItem = CustomItemManager.addCustomMetadata(item, tagName);
        player.getInventory().setItemInMainHand(taggedItem);

        Messenger.prefixedSend(player, "&aItem tagged with identifier &e" + tagName);
    }
}
