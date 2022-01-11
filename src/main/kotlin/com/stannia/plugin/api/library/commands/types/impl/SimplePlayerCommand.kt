package com.stannia.plugin.api.library.commands.types.impl

import com.stannia.plugin.api.library.commands.types.SimpleServerCommand
import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.commands.types.utils.Suggestions
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * A class that handles the proxy from executing as a CommandSender to execute as a Player.
 * This way you don't need to cast the command sender in your command, if you expect them to be a player.
 */
abstract class SimplePlayerCommand : SimpleServerCommand() {

    abstract fun execute(player: Player, command: Command, label: String, args: Args): Boolean
    open fun tabComplete(sender: Player, alias: String, arguments: Args): Suggestions = emptyList()

    /**
     * Proxies the execute method to another execute method where sender is explicitly a Player object.
     */
    override fun execute(sender: CommandSender, command: Command, label: String, args: Args): Boolean {
        val player = sender.asPlayer() ?: return true
        return execute(player, command, label, args)
    }

    /**
     * Proxies the tabComplete method to another execute method where sender is explicitly a Player object.
     */
    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): Suggestions {
        val player = sender.asPlayer() ?: return listOf()
        return tabComplete(player, alias, args)
    }
}