package com.stannia.plugin.example_plugin.commands

import com.stannia.plugin.api.library.commands.types.impl.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.commands.types.utils.Suggestions
import com.stannia.plugin.example_plugin.commands.plugin_config.PluginConfigManagementParentCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class PluginParentCommand(
    override val plugin: JavaPlugin,
    override val name: String = "example",
    override val description: String = "This command has multiple options revolving around the plugin.",
    override val usage: String = "/example <option>"
) : SimpleServerCommand() {

    init {
        registerSubCommands(PluginConfigManagementParentCommand(plugin))
    }

    override fun execute(sender: CommandSender, command: Command, label: String, args: Args) = true
    override fun tabComplete(sender: CommandSender, alias: String, args: Args): Suggestions = emptyList()
}

