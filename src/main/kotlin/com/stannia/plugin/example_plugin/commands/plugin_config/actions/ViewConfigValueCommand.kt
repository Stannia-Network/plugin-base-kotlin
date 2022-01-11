package com.stannia.plugin.example_plugin.commands.plugin_config.actions

import com.stannia.plugin.api.library.commands.types.impl.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.commands.types.utils.Suggestions
import com.stannia.plugin.api.library.extensions.parseColors
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class ViewConfigValueCommand(
    override val plugin: JavaPlugin,
    override val name: String = "view",
    override val description: String = "View current config value."
) : ConfigKeyTabCompleter() {

    override fun execute(sender: CommandSender, command: Command, label: String, args: Args): Boolean {
        if (args.isEmpty()) return false

        val config = plugin.config
        when (config.contains(args[0])) {
            true -> sender.sendMessage("Current value for '&5${args[0]}&f' is:\n&a${config.get(args[0])}".parseColors())
            false -> sender.sendMessage("&cConfig '&5${args[0]}&c' could not be found in the plugin config.".parseColors())
        }

        return true
    }

    override fun tabCompleteConditionHook(args: Args): Suggestions? {
        if (args.size > 1) return listOf("&c&lERROR!&r&c example: '&5messages.player-join&f&c'".parseColors())
        return null
    }
}