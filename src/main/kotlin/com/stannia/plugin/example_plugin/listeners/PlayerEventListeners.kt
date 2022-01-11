package com.stannia.plugin.example_plugin.listeners

import com.stannia.plugin.api.library.events.GameEventListener
import com.stannia.plugin.api.library.extensions.parseColors
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class PlayerEventListeners(override val plugin: JavaPlugin) : GameEventListener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage = (plugin.config.getString("messages.player-join") ?: "&5%player_name just joined the server.")
            .replace("%player_name", event.player.displayName)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = "&9${event.player.displayName}&c just left the server. :(".parseColors()
    }
}