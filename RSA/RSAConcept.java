/*
    This code implements RSA algorithm using Java BigInteger class.
    We have defined functions for all the basic operations which are required to implement
    encryption and decryption like modular exponentiation,modular multiplication
    and modular inverse(using Extended Euclidean Algorithm).
    1. Generate an RSA Public-Private key pair.
    2. Encrypt an arbitrary message using the public key.
    3. Decrypt the message using the private key.
    4. Implement Encryption and Decryption by using user defined functions for 
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

    static boolean millerTest(BigInteger d, BigInteger n)
    {
        BigInteger a=BigInteger.TWO;
        BigInteger x = modexp(a,d,n); 
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
            return true; 
        while (!(d.equals(n.subtract(BigInteger.ONE))))
        {
            x = (x.multiply(x)).mod(n); 
            d=d.multiply(BigInteger.TWO); 
            if (x.equals(BigInteger.ONE)) 
                return false; 
            if (x.equals(n.subtract(BigInteger.ONE)))
                return true; 
        } 
        return false; 
    }

    static boolean isPrime(BigInteger n, int k)
    { 

        if (n.compareTo(BigInteger.ONE)<=0 || n.equals(BigInteger.valueOf(4)))
            return false; 
        if (n.compareTo(BigInteger.valueOf(3))<=0) 
            return true; 
        BigInteger d= n.subtract(BigInteger.ONE);
        while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
        { 
            d=d.divide(BigInteger.TWO);
        }
        for (int i = 0; i < k; i++) 
            if (!millerTest(d, n)) 
                return false; 
        return true; 
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
        System.out.println(isPrime(p, 4));
        q=gen_prime();
        System.out.println(isPrime(q, 4));
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