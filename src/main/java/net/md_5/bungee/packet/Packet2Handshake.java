package net.md_5.bungee.packet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public class Packet2Handshake extends DefinedPacket {

    public String username;

    public Packet2Handshake(String username) {
        super(0x02);
        writeUTF(username);
    }

    public Packet2Handshake(byte[] buf) {
        super(0x02, buf);
        this.username = readUTF();
    }

    @Override
    public void handle(PacketHandler handler) throws Exception {
        handler.handle(this);
    }
}
