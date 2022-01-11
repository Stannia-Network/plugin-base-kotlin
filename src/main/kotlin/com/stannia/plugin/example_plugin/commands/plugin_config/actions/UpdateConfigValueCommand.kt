package com.stannia.plugin.example_plugin.commands.plugin_config.actions

import com.stannia.plugin.api.library.commands.types.impl.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.commands.types.utils.Suggestions
import com.stannia.plugin.api.library.extensions.parseColors
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class UpdateConfigValueCommand(
    override val plugin: JavaPlugin,
    override val name: String = "update",
    override val description: String = "Update a config value.",
    override val usage: String = "/example config update <config_key> <new_value>"
) : ConfigKeyTabCompleter() {

    override fun execute(sender: CommandSender, command: Command, label: String, args: Args): Boolean {
        if (args.size < 2) return false

        val config = plugin.config
        when (config.contains(args[0])) {
            true -> {
                config[args[0]] = args.asList().subList(1, args.size).joinToString(" ")
                sender.sendMessage("Updated '&e${args[0]}&f' value is now:\n&5${config[args[0]]}".parseColors())
            }
            false -> sender.sendMessage("&cConfig '&e${args[0]}&c' could not be found in the plugin config.".parseColors())
        }

        return true
    }
}