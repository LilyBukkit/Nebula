package net.md_5.mendax.datainput;

import java.io.DataInput;
import java.io.IOException;

public class OptionalMotion extends Instruction
{

    @Override
    void read(DataInput in, byte[] buffer) throws IOException
    {
        int data = in.readInt();
        if ( data > 0 )
        {
            skip( in, buffer, 6 );
        }
    }
}
