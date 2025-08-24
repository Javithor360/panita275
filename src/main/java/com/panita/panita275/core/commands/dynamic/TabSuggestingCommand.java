package com.panita.panita275.core.commands.dynamic;

import com.panita.panita275.core.commands.identifiers.CommandMeta;

/**
 * Interface for commands that can suggest tab completions.
 */
public interface TabSuggestingCommand {
    void applySuggestions(CommandMeta meta);
}
