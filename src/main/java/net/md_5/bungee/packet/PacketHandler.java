package net.md_5.bungee.packet;

import ru.vladthemountain.nebula.packet.Packet81ULPPLoggedIn;
import ru.vladthemountain.nebula.packet.Packet82RosepadMeta;

public abstract class PacketHandler {

    private void nop(DefinedPacket packet) {
        throw new UnsupportedOperationException("No handler defined for packet " + packet.getClass());
    }

    public void handle(Packet0KeepAlive alive) throws Exception {
        nop(alive);
    }

    public void handle(Packet1Login login) throws Exception {
        nop(login);
    }

    public void handle(Packet2Handshake handshake) throws Exception {
        nop(handshake);
    }

    public void handle(Packet3Chat chat) throws Exception {
        nop(chat);
    }

    public void handle(PacketCDClientStatus clientStatus) throws Exception {
        nop(clientStatus);
    }

    public void handle(PacketFAPluginMessage pluginMessage) throws Exception {
        nop(pluginMessage);
    }

    public void handle(PacketFCEncryptionResponse encryptResponse) throws Exception {
        nop(encryptResponse);
    }

    public void handle(PacketFDEncryptionRequest encryptRequest) throws Exception {
        nop(encryptRequest);
    }

    public void handle(PacketFEPing ping) throws Exception {
        nop(ping);
    }

    // Nebula start
    public void handle(Packet81ULPPLoggedIn ulppLoggedIn) throws Exception {
        nop(ulppLoggedIn);
    }

    public void handle(Packet82RosepadMeta rosepadMeta) throws Exception {
        nop(rosepadMeta);
    }
    // Nebula end

    public void handle(PacketFFKick kick) throws Exception {
        nop(kick);
    }
}
