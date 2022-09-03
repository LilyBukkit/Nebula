package net.md_5.bungee.packet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public class PacketFFKick extends DefinedPacket
{

    public String reason;

    public PacketFFKick(String reason)
    {
        super( 0xFF );
        writeUTF(reason);
    }

    public PacketFFKick(byte[] buf)
    {
        super( 0xFF, buf );
        this.reason = readUTF();
    }

    @Override
    public void handle(PacketHandler handler) throws Exception
    {
        handler.handle( this );
    }
}
