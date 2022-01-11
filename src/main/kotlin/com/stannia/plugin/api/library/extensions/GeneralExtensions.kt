package com.stannia.plugin.api.library.extensions

import org.bukkit.ChatColor

fun String.parseColors(colorCharacter: Char = '&')
        = ChatColor.translateAlternateColorCodes(colorCharacter, this)