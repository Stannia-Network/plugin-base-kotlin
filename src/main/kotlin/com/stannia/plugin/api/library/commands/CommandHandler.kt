package com.stannia.plugin.api.library.commands

import com.stannia.plugin.api.library.commands.types.SimpleServerCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

//private val logger = InlineLogger()

/**
 * Command handler that will register all the commands for the server plugins using this library.
 * It will also register the plugins to the server, instead of having to register them manually and then defining them in the plugin.yml-file.
 */
class CommandHandler(
    private val commands: MutableMap<String, SimpleServerCommand> = mutableMapOf()
) {

    /**
     * Registering a ChatCommand to both this command handler and the game server's command handler.
     */
    private fun register(plugin: JavaPlugin, chatCommand: SimpleServerCommand): CommandHandler {
        //logger.debug { "&eRegistering command: &a$chatCommand&c..." }
        Bukkit.getLogger().log(Level.INFO, "&eRegistering command: &a$chatCommand&c...")
        commands[chatCommand.name] = chatCommand
        plugin.register(chatCommand)
        //when (plugin.register(chatCommand)) {
        //    true -> logger.debug { "&aRegistered command: &2$chatCommand&a!".parseColors() }
        //    false -> logger.error { "&cFailed to register command: &4$chatCommand&c!".parseColors() }
        //}
        return this
    }

    /**
     * Register multiple ChatCommands to both this command handler and game server's command handler.
     */
    fun register(vararg commands: SimpleServerCommand): CommandHandler {
        commands.forEach { register(it.plugin, it) }
        return this
    }

    /**
     * Extension method to make the registration to server's command handler a bit cleaner.
     */
    private fun JavaPlugin.register(command: SimpleServerCommand) = commandMap().register(this.name, command.forBukkit())

    /**
     * Find CraftServer by server version and return the commandMap by use of reflection
     */
    private fun commandMap(): CommandMap {
        // Gets current server version.
        val version = Bukkit.getServer().javaClass.packageName.split(".")[3]
        // We need the CraftServer class in order to get the inaccessible commandMap field.
        val obcClass = Class.forName("org.bukkit.craftbukkit.$version.CraftServer")

        // We now need to get the field through reflection, and then use the Bukkit server instance
        // to get the commandMap instance we use for registering.
        return with(obcClass.getDeclaredField("commandMap")) {
            this.isAccessible = true
            this.get(Bukkit.getServer()) as CommandMap
        }
    }
}