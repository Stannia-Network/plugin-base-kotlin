package com.stannia.plugin.example_plugin.commands.plugin_config

import com.stannia.plugin.api.library.commands.types.impl.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.commands.types.utils.Suggestions
import com.stannia.plugin.example_plugin.commands.plugin_config.actions.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class PluginConfigManagementParentCommand(
    override val plugin: JavaPlugin,
    override val name: String = "config",
    override val description: String = "A set of commands to manage your plugin's config values."
) : SimpleServerCommand() {

    init {
        registerSubCommands(
            ReloadConfigFileCommand(plugin),
            ResetConfigFileCommand(plugin),
            UpdateConfigValueCommand(plugin),
            ViewConfigValueCommand(plugin),
            SaveConfigCommand(plugin)
        )
    }

    override fun execute(sender: CommandSender, command: Command, label: String, args: Args) = true
    override fun tabComplete(sender: CommandSender, alias: String, args: Args): Suggestions = emptyList()
}