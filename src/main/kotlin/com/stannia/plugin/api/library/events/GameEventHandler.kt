package com.stannia.plugin.api.library.events

import com.stannia.plugin.api.library.extensions.parseColors
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

//private val logger = InlineLogger()

/**
 * Basic extension of Bukkit's Listener
 *
 * @see Listener
 *
 * @property plugin The owning JavaPlugin, so it can also be used from within the event class easily.
 */
interface GameEventListener : Listener {
    val plugin: JavaPlugin
}

class GameEventHandler(
    private val listeners: MutableList<GameEventListener> = mutableListOf()
) {
    private fun GameEventListener.register(): GameEventListener {
        //logger.debug { "Registering game event listener, ${this::class.simpleName}." }
        Bukkit.getServer().pluginManager.registerEvents(this, this.plugin)
        return this
    }

    fun register(vararg registerListeners: GameEventListener): GameEventHandler {
        //logger.debug { "Registering ${listeners.size} game event listeners." }
        listeners.addAll(registerListeners)
        registerListeners.forEach { it.register() }
        //logger.info { "&aSuccessfully registered &9${listeners.size}&a game event listeners.".parseColors() }
        return this
    }
}