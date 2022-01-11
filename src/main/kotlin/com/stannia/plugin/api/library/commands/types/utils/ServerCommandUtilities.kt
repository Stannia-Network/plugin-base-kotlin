package com.stannia.plugin.api.library.commands.types.utils

import com.stannia.plugin.api.library.commands.types.SimpleServerCommand
import org.bukkit.command.CommandSender

typealias Executor = CommandSender
typealias Args = Array<out String>
typealias CmdWithArgs = Pair<SimpleServerCommand, Args>
typealias Suggestions = List<String>