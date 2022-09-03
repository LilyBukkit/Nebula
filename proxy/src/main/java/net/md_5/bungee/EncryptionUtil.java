package net.md_5.bungee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.md_5.bungee.packet.PacketFCEncryptionResponse;
import net.md_5.bungee.packet.PacketFDEncryptionRequest;

/**
 * Class containing all encryption related methods for the proxy.
 */
public class EncryptionUtil
{

    private static final Random random = new Random();
    private static KeyPair keys;

    public static PacketFDEncryptionRequest encryptRequest() throws NoSuchAlgorithmException
    {
        if ( keys == null )
        {
            keys = KeyPairGenerator.getInstance( "RSA" ).generateKeyPair();
        }

        String hash = ( BungeeCord.getInstance().config.isOnlineMode() ) ? Long.toString( random.nextLong(), 16 ) : "-";
        byte[] pubKey = keys.getPublic().getEncoded();
        byte[] verify = new byte[ 4 ];
        random.nextBytes( verify );
        return new PacketFDEncryptionRequest( hash, pubKey, verify );
    }

    public static SecretKey getSecret(PacketFCEncryptionResponse resp, PacketFDEncryptionRequest request) throws BadPaddingException, IllegalBlockSizeException, IllegalStateException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance( "RSA" );
        cipher.init( Cipher.DECRYPT_MODE, keys.getPrivate() );
        byte[] decrypted = cipher.doFinal( resp.verifyToken );

        if ( !Arrays.equals( request.verifyToken, decrypted ) )
        {
            throw new IllegalStateException( "Key pairs do not match!" );
        }

        cipher.init( Cipher.DECRYPT_MODE, keys.getPrivate() );
        byte[] shared = resp.sharedSecret;
        byte[] secret = cipher.doFinal( shared );

        return new SecretKeySpec( secret, "AES" );
    }

    public static boolean isAuthenticated(String username, String connectionHash, SecretKey shared) throws NoSuchAlgorithmException, IOException
    {
        String encName = URLEncoder.encode( username, "UTF-8" );

        MessageDigest sha = MessageDigest.getInstance( "SHA-1" );
        for ( byte[] bit : new byte[][]
        {
            connectionHash.getBytes( "ISO_8859_1" ), shared.getEncoded(), keys.getPublic().getEncoded()
        } )
        {
            sha.update( bit );
        }

        String encodedHash = URLEncoder.encode( new BigInteger( sha.digest() ).toString( 16 ), "UTF-8" );
        String authURL = "http://session.minecraft.net/game/checkserver.jsp?user=" + encName + "&serverId=" + encodedHash;
        String reply;
        try ( BufferedReader in = new BufferedReader( new InputStreamReader( new URL( authURL ).openStream() ) ) )
        {
            reply = in.readLine();
        }

        return "YES".equals( reply );
    }

    public static Cipher getCipher(int opMode, Key shared) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cip = Cipher.getInstance( "AES/CFB8/NoPadding" );
        cip.init( opMode, shared, new IvParameterSpec( shared.getEncoded() ) );
        return cip;
    }
}
