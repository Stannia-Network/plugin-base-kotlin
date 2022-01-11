package com.stannia.plugin.api.library.commands.types

import com.stannia.plugin.api.library.commands.types.utils.Args
import com.stannia.plugin.api.library.commands.types.utils.CmdWithArgs
import com.stannia.plugin.api.library.commands.types.utils.Executor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * A base class for commands, since we have multiple command types.
 *
 * @see SimpleServerCommand
 * @see AdvancedServerCommand
 *
 * @param subCommands A map of sub commands for this command
 *
 * @property name Command name to be used in game. **_/name arg1 arg2 ..._**
 * @property description Describes what the command does
 * @property usage How to use the command
 * @property permission Permission required to use this command, null means anyone can use it
 * @property permissionMessage The message to send players when they don't have permission
 */
abstract class SimpleServerCommand(
    private val subCommands: MutableMap<String, SimpleServerCommand> = mutableMapOf()
) {
    abstract val plugin: JavaPlugin
    abstract val name: String
    abstract val description: String
    open val usage: String? = null
    open val aliases: List<String> = listOf()
    open val permission: String? = null
    open val permissionMessage: String = "You don't have permission to use this command."

    open fun tabComplete(sender: CommandSender, alias: String, args: Args) = listOf<String>()
    abstract fun execute(sender: CommandSender, command: Command, label: String, args: Args): Boolean

    private fun processCommandTree(command: SimpleServerCommand, arguments: Args): CmdWithArgs {
        var executingCommand = command
        var remainingArguments = arguments

        while (
            executingCommand.subCommands.isNotEmpty() && remainingArguments.isNotEmpty() &&
            executingCommand.subCommands.containsKey(remainingArguments.first())
        ) {
            executingCommand = executingCommand.subCommands[remainingArguments.first()]
                ?: error("Uh oh! You need to investigate, this isn't supposed to be possible!")

            remainingArguments = remainingArguments.asList().subList(1, remainingArguments.size).toTypedArray()
        }

        return Pair(executingCommand, remainingArguments)
    }

    /**
     * Recursively execute the command logic for this command and its sub-commands.
     */
    private fun executeCommandTree(sender: Executor, command: Command, alias: String, arguments: Args) =
        with(processCommandTree(this, arguments)) {
            this.first.execute(sender, command, alias, this.second)
        }

    /**
     * Recursively execute the tab completion for this command and its sub-commands.
     */
    private fun executeCommandTree(sender: Executor, alias: String, arguments: Args) =
        with(processCommandTree(this, arguments)) {
            this.first.tabComplete(sender, alias, this.second) + this.first.subCommands.keys.filter {
                if (this.second.isEmpty()) true else
                it.equals(this.second.first(), true) || it.contains(this.second.first())
            }
        }

    override fun toString(): String {
        return "${this::class.simpleName}(name=$name)"
    }

    fun registerSubCommands(vararg commands: SimpleServerCommand) {
        commands.forEach {
            subCommands[it.name] = it
        }
    }

    fun Executor.asPlayer(sendMessage: Boolean = false): Player? {
        if (this is Player) return this
        if (sendMessage) this.sendMessage("You need to be a player to execute this command.")
        return null
    }

    /**
     * Gets the Bukkit command instance, so we can register it to the server's command handler.
     */
    fun forBukkit() = object : Command(this.name, this.description, usage ?: "", this.aliases) {
        override fun execute(sender: Executor, label: String, arguments: Args) =
            executeCommandTree(sender, this, label, arguments)

        override fun tabComplete(sender: Executor, alias: String, arguments: Args) =
            executeCommandTree(sender, alias, arguments)
    }
}