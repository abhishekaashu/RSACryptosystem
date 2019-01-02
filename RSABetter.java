import java.util.*;
import java.math.*;
class RSABetter
{
    private BigInteger p,q,n,phi,e,d;
    int bitlength=1024;
    public RsaFinal()
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
        RsaFinal rsa=new RsaFinal();
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