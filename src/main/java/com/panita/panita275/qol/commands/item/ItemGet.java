package com.panita.panita275.qol.commands.item;

import com.panita.panita275.core.chat.Messenger;
import com.panita.panita275.core.commands.dynamic.AdvancedCommand;
import com.panita.panita275.core.commands.dynamic.TabSuggestingCommand;
import com.panita.panita275.core.commands.identifiers.CommandMeta;
import com.panita.panita275.core.commands.identifiers.SubCommandSpec;
import com.panita.panita275.core.util.CommandUtils;
import com.panita.panita275.qol.util.CustomItemManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

@SubCommandSpec(
        parent = "panitacraft item",
        name = "get",
        description = "Get a custom saved item by name.",
        syntax = "/panitacraft item get <item_name>",
        permission = "panitacraft.command.item.get",
        playerOnly = true
)
public class ItemGet implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        Player player = (Player) sender;
        String itemName = args[0];

        ItemStack item = CustomItemManager.getItem(itemName);

        if (item == null) {
            Messenger.prefixedSend(player, "&cNo custom item found with name &e" + itemName);
            return;
        }

        player.getInventory().addItem(item.clone());
        Messenger.prefixedSend(player, "&aYou have received the item &e" + itemName);
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return CustomItemManager.getAllItemNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
