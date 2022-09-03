package net.md_5.bungee.config;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ConfigurationAdapter;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Core configuration for the proxy.
 */
@Getter
public class Configuration {
    /**
     * Time before users are disconnected due to no network activity.
     */
    private int timeout = 30000;
    /**
     * UUID used for metrics.
     */
    private String uuid = UUID.randomUUID().toString();
    /**
     * Set of all listeners.
     */
    private Collection<ListenerInfo> listeners;
    /**
     * Set of all servers.
     */
    private Map<String, ServerInfo> servers;
    /**
     * Should we check minecraft.net auth.
     */
    private boolean onlineMode = true;
    private int sleepTime = 1;

    public void load() {
        ConfigurationAdapter adapter = ProxyServer.getInstance().getConfigurationAdapter();
        adapter.load();

        timeout = adapter.getInt("timeout", timeout);
        uuid = adapter.getString("stats", uuid);
        onlineMode = adapter.getBoolean("online_mode", onlineMode);
        sleepTime = adapter.getInt("sleep_time", sleepTime);

        listeners = adapter.getListeners();
        Preconditions.checkArgument(listeners != null && !listeners.isEmpty(), "No listeners defined.");

        servers = adapter.getServers();
        Preconditions.checkArgument(servers != null && !servers.isEmpty(), "No servers defined");

        for (ListenerInfo listener : listeners) {
            Preconditions.checkArgument(servers.containsKey(listener.getDefaultServer()));
        }
    }
}
