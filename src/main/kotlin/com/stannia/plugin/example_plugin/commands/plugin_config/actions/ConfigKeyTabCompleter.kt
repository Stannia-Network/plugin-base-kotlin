package com.stannia.plugin.example_plugin.commands.plugin_config.actions

import com.stannia.plugin.api.library.commands.types.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.commands.types.utils.Suggestions
import com.stannia.plugin.api.library.extensions.parseColors
import org.bukkit.command.CommandSender

abstract class ConfigKeyTabCompleter : SimpleServerCommand() {

    /**
     * Tab completion that will show the possible configuration keys.
     * It will also warn users if they try to search with space.
     */
    override fun tabComplete(sender: CommandSender, alias: String, args: Args): Suggestions {
        if (args.isEmpty()) return emptyList()

        val hookResult = tabCompleteConditionHook(args)
        if (hookResult != null) return hookResult

        val levels = args[0].split("\\.")
        val searchTerm = levels.subList(0, levels.size - 1).joinToString(".")

        return (plugin.config.getConfigurationSection(searchTerm)?.getKeys(true)?.toList() ?: emptyList<String>())
            .filter { it.contains(args[0]) || it == args[0] }
    }

    open fun tabCompleteConditionHook(args: Args): Suggestions? = null
}