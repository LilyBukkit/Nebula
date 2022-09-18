package net.md_5.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;

/**
 * Command to list all players connected to the proxy.
 */
public class CommandList extends Command {

    public CommandList() {
        super("glist", "bungeecord.command.list");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        StringBuilder users = new StringBuilder();
        Collection<ProxiedPlayer> connections = ProxyServer.getInstance().getPlayers();

        if (connections.isEmpty()) {
            sender.sendMessage(ChatColor.BLUE + "Currently no players online.");
            return;
        }

        for (ProxiedPlayer player : connections) {
            users.append(player.getDisplayName());
            users.append(", ");
            users.append(ChatColor.RESET);
        }

        users.setLength(users.length() - 2);
        sender.sendMessage(ChatColor.BLUE + "Currently online across all servers (" + connections.size() + "): " + ChatColor.RESET + users);
    }
}
