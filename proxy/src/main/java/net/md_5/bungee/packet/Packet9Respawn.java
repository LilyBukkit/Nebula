package net.md_5.bungee.packet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public class Packet9Respawn extends DefinedPacket
{

    public int dimension;
    public byte difficulty;
    public byte gameMode;
    public short worldHeight;
    public String levelType;

    public Packet9Respawn(int dimension, byte difficulty, byte gameMode, short worldHeight, String levelType)
    {
        super( 0x09 );
        writeInt( dimension );
        writeByte( difficulty );
        writeByte( gameMode );
        writeShort( worldHeight );
        writeUTF( levelType );
    }

    public Packet9Respawn(byte[] buf)
    {
        super( 0x09, buf );
        this.dimension = readInt();
        this.difficulty = readByte();
        this.gameMode = readByte();
        this.worldHeight = readShort();
        this.levelType = readUTF();
    }

    @Override
    public void handle(PacketHandler handler) throws Exception
    {
        handler.handle( this );
    }
}
