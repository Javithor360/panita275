package com.panita.panita275.qol.commands.item;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.core.util.CommandUtils;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.command.CommandSender;

@SubCommandSpec(
        parent = "panitacraft item",
        name = "remove",
        description = "Removes a saved custom item from the JSON file.",
        syntax = "/panitacraft item remove <item_name>",
        playerOnly = false
)
public class ItemRemove implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        String itemName = args[0];
        CustomItemManager.ItemResult result = CustomItemManager.removeItem(itemName);

        switch (result) {
            case SUCCESS -> Messenger.prefixedSend(sender, "&aItem &e" + itemName + " &ahas been removed successfully.");
            case NOT_FOUND -> Messenger.prefixedSend(sender, "&cNo custom item found with the name &e" + itemName);
            case ERROR -> Messenger.prefixedSend(sender, "&cAn unexpected error occurred while removing the item.");
        }
    }
}
