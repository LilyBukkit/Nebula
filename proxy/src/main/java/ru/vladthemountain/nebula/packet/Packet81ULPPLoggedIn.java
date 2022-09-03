package ru.vladthemountain.nebula.packet;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.md_5.bungee.packet.DefinedPacket;
import net.md_5.bungee.packet.PacketHandler;

@ToString
@EqualsAndHashCode(callSuper = false)
public class Packet81ULPPLoggedIn extends DefinedPacket {

    public Packet81ULPPLoggedIn(byte[] buff) {
        super(0x81, buff);
    }

    @Override
    public void handle(PacketHandler handler) throws Exception {
        handler.handle(this);
    }
}
