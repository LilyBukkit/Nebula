package net.md_5.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class CommandBungee extends Command {

    public CommandBungee() {
        super("nebula");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.BLUE + "This server is running Nebula version " + ProxyServer.getInstance().getVersion() + " by The LilyBukkit Project. Original BungeeCord is made by md_5");
    }
}
