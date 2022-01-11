package com.stannia.plugin.example_plugin.commands.plugin_config.actions

import com.stannia.plugin.api.library.commands.types.impl.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.extensions.parseColors
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class ResetConfigFileCommand(
    override val plugin: JavaPlugin,
    override val name: String = "reset",
    override val description: String = "Reset the config file & create a backup of old."
) : SimpleServerCommand() {

    override fun execute(sender: CommandSender, command: Command, label: String, args: Args): Boolean {
        return true
    }
}