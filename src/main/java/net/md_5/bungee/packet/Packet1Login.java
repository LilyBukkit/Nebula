package net.md_5.bungee.packet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public class Packet1Login extends DefinedPacket {

    public int protocolVersion;
    public String username;
    public String password;

    public Packet1Login(int protocolVer, String playerName, String pass) {
        super(0x01);
        writeInt(protocolVer);
        writeUTF(playerName);
        writeUTF(pass);
    }

    public Packet1Login(byte[] buf) {
        super(0x01, buf);
        this.protocolVersion = readInt();
        this.username = readUTF();
        this.password = readUTF();
    }

    @Override
    public void handle(PacketHandler handler) throws Exception {
        handler.handle(this);
    }
}
