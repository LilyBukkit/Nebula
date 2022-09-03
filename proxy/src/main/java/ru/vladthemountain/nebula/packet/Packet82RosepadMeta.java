package ru.vladthemountain.nebula.packet;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.md_5.bungee.packet.DefinedPacket;
import net.md_5.bungee.packet.PacketHandler;
import ru.vladthemountain.nebula.api.rosepad.ULPPExtension;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
public class Packet82RosepadMeta extends DefinedPacket {

    public List<ULPPExtension> extensions = new ArrayList<>();
    public String sideName;
    public String host; //Nebula uses this field for both IPv4 & port
    public int[] version;
    public String tag;
    public long flags;

    public Packet82RosepadMeta(String name, String tag, String host, int[] ver, List<ULPPExtension> exts, long flags) {
        super(0x82);
        writeUTF(name);
        writeUTF(tag);
        writeUTF(host);
        writeShort(ver.length);
        for (int i : ver) {
            writeShort(i);
        }
        writeInt(exts.size());
        for (ULPPExtension e : exts) {
            writeUTF(e.getName());
            writeInt(e.getVersion());
        }
        writeLong(flags);
    }

    public Packet82RosepadMeta(byte[] buff) {
        super(0x82, buff);
        this.sideName = readUTF();
        this.tag = readUTF();
        this.host = readUTF();
        short verLen = readShort();
        this.version = new int[verLen];
        for (short i = 0; i < verLen; i++) {
            this.version[i] = readShort();
        }
        int extCount = readInt();
        this.extensions.clear();
        for (int i = 0; i < extCount; i++) {
            this.extensions.add(new ULPPExtension(readUTF(), readInt()));
        }
        this.flags = readLong();
    }

    @Override
    public void handle(PacketHandler handler) throws Exception {
        handler.handle(this);
    }
}
