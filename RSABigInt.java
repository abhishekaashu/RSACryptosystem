/*
    This code implements RSA algorithm without using Java BigInteger class and its pre-defined functions.
    1. Generate an RSA Public-Private key pair.
    2. Encrypt an arbitrary message using the public key.
    3. Decrypt the message using the private key.
	4. Implement modular exponentiation,modular multiplication and
	   modular inverse(using Extended Euclidean Algorithm) using pre-defined funtions.
*/
import java.util.*;
import java.math.*;
class RSABigInt
{
    private BigInteger p,q,n,phi,e,d;
    int bitlength=1024;
    public RSABigInt()
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
		d = e.modInverse(phi);
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
        RSABigInt rsa=new RSABigInt();
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