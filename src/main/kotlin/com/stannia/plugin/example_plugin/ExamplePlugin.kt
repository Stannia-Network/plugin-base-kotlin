package com.stannia.plugin.example_plugin

import com.stannia.plugin.example_plugin.commands.PluginParentCommand
import com.stannia.plugin.example_plugin.listeners.PlayerEventListeners
import com.stannia.plugin.api.library.commands.CommandHandler
import com.stannia.plugin.api.library.events.GameEventHandler
import org.bukkit.plugin.java.JavaPlugin

class ExamplePlugin : JavaPlugin() {

    val commandHandler = CommandHandler()
    val eventHandler = GameEventHandler()

    override fun onEnable() {
        saveDefaultConfig()

        commandHandler.register(
            // Dummy Example Commands

            // Actual Commands
            PluginParentCommand(plugin = this),
        )

        eventHandler.register(
            PlayerEventListeners(plugin = this)
        )
    }
}