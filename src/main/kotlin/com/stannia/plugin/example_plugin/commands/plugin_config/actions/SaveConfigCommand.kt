package com.stannia.plugin.example_plugin.commands.plugin_config.actions

import com.stannia.plugin.api.library.commands.types.impl.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class SaveConfigCommand(
    override val plugin: JavaPlugin,
    override val name: String = "save",
    override val description: String = "Reset the config file & create a backup of old."
) : SimpleServerCommand() {

    override fun execute(sender: CommandSender, command: Command, label: String, args: Args): Boolean {
        val time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss").withZone(ZoneOffset.ofHours(1)).format(Instant.now())
        File(plugin.dataFolder.path + "/config.yml").copyTo(File(plugin.dataFolder.path + "/config_history/config_until-$time.yml"))
        plugin.config.save(plugin.dataFolder.path + "/config.yml")
        sender.sendMessage("&aSuccessfully saved the new config file. Created a backup of the old file to be safe.")
        return true
    }
}