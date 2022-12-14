package net.md_5.bungee.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

/**
 * Command to list and switch a player between available servers.
 */
public class CommandServer extends Command {

    public CommandServer() {
        super("server", "bungeecord.command.server");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        Map<String, ServerInfo> servers = BungeeCord.getInstance().config.getServers();
        if (args.length == 0) {
            StringBuilder serverList = new StringBuilder();
            for (String server : servers.keySet()) {
                serverList.append(server);
                serverList.append(", ");
            }
            serverList.setLength(serverList.length() - 2);
            player.sendMessage(ChatColor.GOLD + "You may connect to the following servers at this time: " + serverList);
        } else {
            ServerInfo server = servers.get(args[0]);
            if (server == null) {
                player.sendMessage(ChatColor.RED + "The specified server does not exist");
            } else if (server.equals(player.getServer().getInfo())) {
                player.sendMessage(ChatColor.RED + "You are already on this server.");
            } else {
                player.connect(server);
            }
        }
    }
}
