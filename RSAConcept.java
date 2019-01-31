/*
    This code implements RSA algorithm using Java BigInteger class.
    1. Generate an RSA Public-Private key pair.
    2. Encrypt an arbitrary message using the public key.
    3. Decrypt the message using the private key.
    4. Implement Encryption and Decryption by using user defined functions for modular exponentiation,
       modular multiplication, modular inverse(using Extended Euclidean Algorithm).
*/
import java.util.*;
import java.math.*;
class RSAConcept
{
    private BigInteger p,q,n,phi,e,d;
    int bitlength=1024;


    //(x^y)%p
    static BigInteger modexp(BigInteger x,BigInteger y,BigInteger p)
    {
        BigInteger res=BigInteger.valueOf(1);
        x=x.mod(p);
        while(y.compareTo(BigInteger.ZERO)==1)
        {
            if((y.mod(BigInteger.TWO)).compareTo(BigInteger.ONE)==0)//y is odd
            {
                res=(res.multiply(x)).mod(p);
            }
            y=y.shiftRight(1);//y=y/2
            x=(x.multiply(x)).mod(p);
        }
        return res;
    }

    //(x*y)%p
    static BigInteger modmult(BigInteger x,BigInteger y,BigInteger p)
    {
        BigInteger res=BigInteger.valueOf(0);
        
        x=x.mod(p);
        while(y.compareTo(BigInteger.ZERO)==1)
        {
            if(y.mod(BigInteger.TWO).compareTo(BigInteger.ONE)==0)//y is odd
            res=(res.add(x)).mod(p);
            x=(x.multiply(BigInteger.TWO)).mod(p);
            y=y.shiftRight(1);//y=y/2
        }
        return res;
    }

    static BigInteger modInv(BigInteger a,BigInteger m) 
    { 
        BigInteger m0 = m; 
        BigInteger y=BigInteger.valueOf(0);
        BigInteger x =BigInteger.valueOf(1); 
  
        if (m.compareTo(BigInteger.ONE)==0) 
            return BigInteger.ZERO; 
  
        while (a.compareTo(BigInteger.ONE) > 0)
        { 
            // q is quotient 
            BigInteger q = a.divide(m); 
  
            BigInteger t = m; 
  
            // m is remainder now, process 
            // same as Euclid's algo 
            m = a.mod(m); 
            a = t; 
            t = y; 
  
            // Update x and y 
            y = x.subtract(q.multiply(y)); 
            x = t; 
        } 
  
        // Make x positive 
        if (x.compareTo(BigInteger.ZERO)<0) 
            x=x.add(m0); 
  
        return x; 
    }

    BigInteger gen_prime()
    {
        Random rnd=new Random();
        BigInteger a=BigInteger.probablePrime(bitlength,rnd);
        return a;
    }

    void rsa_keygen()
    {
        p=gen_prime();
        q=gen_prime();
        n=p.multiply(q);
        e = BigInteger.valueOf(2);
		phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
		while (e.compareTo(phi)<0) 
		{
			if ((phi.gcd(e)).equals(BigInteger.ONE))
				break;
			else
				e=e.add(BigInteger.ONE);
        }
        d=modInv(e,phi);
        System.out.println(d);
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
        return modexp(m,e, n).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message)
    {
        BigInteger m=new BigInteger(message);
        return modexp(m,d, n).toByteArray();
    }

	public static void main(String args[])
	{
        
        RSAConcept rsa=new RSAConcept();
        rsa.rsa_keygen();
        String teststring = "Sahil";
		String a=bytesToString(teststring.getBytes());
		System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: "+a);
        byte[] encrypted = rsa.encrypt(teststring.getBytes());
        byte[] decrypted = rsa.decrypt(encrypted);
		System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
		String sahil=new String(decrypted);
        System.out.println("Decrypted String: " + sahil);   
	}
}