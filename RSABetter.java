/********************************************************
   1. Generate an RSA Public-Private key pair.
   2. Encrypt an arbitrary message using the public key.
   3. Decrypt the message using the private key. 

Experiment the above with
 In addition to the time measurements for key generation, encryption and decryption AND analysis of results please submit an appendix which includes:

    * Your Java code.
    * A sample key and message with key generation time and encryption / decryption time.
    * A script file to execute your program. 
********************************************************/
import java.util.*;
import java.math.*;
class RsaBetter
{
    private BigInteger p,q,n,phi,e,d;
    int bitlength=1024;
    public RsaBetter()
    {
        Random rnd = new Random();
		p=BigInteger.probablePrime(bitlength,rnd);
		q=BigInteger.probablePrime(bitlength,rnd);
		n = p.multiply(q);
		e = BigInteger.valueOf(2);
		phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
		while (e.compareTo(phi)<1) 
		{
			if ((phi.gcd(e)).equals(BigInteger.ONE))
				break;
			else
				e=e.add(BigInteger.ONE);
		}
		//System.out.println("Public Key "+n+" "+e);
		d = e.modInverse(phi);
		//System.out.println("Private Key "+d);
    }

	private static String bytesToString(byte[] encrypted)
    {
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
	}
	
	// Encrypt message
    public byte[] encrypt(byte[] message)
    {
        BigInteger m=new BigInteger(message);
        return m.modPow(e, n).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message)
    {
        BigInteger m=new BigInteger(message);
        return m.modPow(d, n).toByteArray();
    }
	

	public static void main(String args[])
	{
        RsaBetter rsa=new RsaBetter();
		String teststring = "Sahil";
		String a=bytesToString(teststring.getBytes());
		System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: "+a);
        // encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes());
        // decrypt
        byte[] decrypted = rsa.decrypt(encrypted);
		System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
		String sahil=new String(decrypted);
        System.out.println("Decrypted String: " + sahil);
    }
}