package net.md_5.bungee.api.connection;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * Represents a destination which this proxy might connect to.
 */
public interface Server extends Connection
{

    /**
     * Returns the basic information about this server.
     *
     * @return the {@link ServerInfo} for this server
     */
    public ServerInfo getInfo();

    /**
     * Send data by any available means to this server.
     *
     * @param channel the channel to send this data via
     * @param data the data to send
     */
    public abstract void sendData(String channel, byte[] data);

    /**
     * Asynchronously gets the current player count on this server.
     *
     * @param callback the callback to call when the count has been retrieved.
     * @deprecated use the corresponding method in {@link ServerInfo} for
     * clarity
     */
    @Deprecated
    public abstract void ping(Callback<ServerPing> callback);
}
