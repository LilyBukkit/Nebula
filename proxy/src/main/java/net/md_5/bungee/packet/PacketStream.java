package net.md_5.bungee.packet;

import lombok.Getter;
import lombok.Setter;
import net.md_5.mendax.datainput.DataInputPacketReader;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A specialized input stream to parse packets using the Mojang packet
 * definitions and then return them as a byte array.
 */
public class PacketStream implements AutoCloseable {

    private final DataInputStream dataInput;
    @Getter
    private OutputStream out;
    @Getter
    @Setter
    private int protocol;
    private final TrackingInputStream tracker;
    private final byte[] buffer = new byte[1 << 18];

    public PacketStream(InputStream in, int protocol) {
        this(in, null, protocol);
    }

    public PacketStream(InputStream in, OutputStream out, int protocol) {
        tracker = new TrackingInputStream(in);
        dataInput = new DataInputStream(tracker);
        this.out = out;
        this.protocol = protocol;
    }

    public void write(byte[] b) throws IOException {
        out.write(b);
    }

    public void write(DefinedPacket packet) throws IOException {
        out.write(packet.getPacket());
    }

    /**
     * Read an entire packet from the stream and return it as a byte array.
     *
     * @return the read packet
     * @throws IOException when the underlying input stream throws an exception
     */
    public byte[] readPacket() throws IOException {
        tracker.out.reset();
        DataInputPacketReader.readPacket(dataInput, buffer, protocol);
        return tracker.out.toByteArray();
    }

    @Override
    public void close() throws Exception {
        dataInput.close();
    }

    /**
     * Input stream which will wrap another stream and copy all bytes read to a
     * {@link ByteArrayOutputStream}.
     */
    private class TrackingInputStream extends FilterInputStream {

        private final ByteArrayOutputStream out = new ByteArrayOutputStream();

        public TrackingInputStream(InputStream in) {
            super(in);
        }

        @Override
        public int read() throws IOException {
            int ret = in.read();
            out.write(ret);
            return ret;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int ret = in.read(b, off, len);
            if (b.length > off && ret - off < b.length) //Nebula
                out.write(b, off, ret);
            return ret;
        }
    }
}
