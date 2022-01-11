package com.stannia.plugin.example_plugin.commands.example;

import com.stannia.plugin.api.library.commands.types.impl.SimplePlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class JavaExampleCommand extends SimplePlayerCommand {

    private final JavaPlugin plugin;

    public JavaExampleCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @NotNull
    @Override
    public String getName() {
        return "java-example";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "This is a Java example command.";
    }

    @Override
    public boolean execute(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        player.sendMessage("Hello, you just executed a Java-based command!");
        return false;
    }
}
