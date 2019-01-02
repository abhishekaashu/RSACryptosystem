import java.util.*;
import java.math.*;
class RSABigInt
{
	static int convert(String s)
	{
		int m=0;
		int l=s.length();
		for(int i=0;i<l;i++)
		{
			char ch=s.charAt(i);
			int c=(int)ch-64;
			m=m*10+c;
		}
		return m;
	}

	public static void main(String args[])
	{
		Random rnd = new Random();
		BigInteger p=BigInteger.probablePrime(1024,rnd);
		BigInteger q=BigInteger.probablePrime(1024,rnd);
		BigInteger n = p.multiply(q);
		BigInteger e = BigInteger.valueOf(2);
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
		while (e.compareTo(phi)<1) 
		{
			if ((phi.gcd(e)).equals(BigInteger.ONE))
				break;
			else
				e=e.add(BigInteger.ONE);
		}
		BigInteger c,d;
		System.out.println("Public Key "+n+" "+e);
		d = e.modInverse(phi);
		System.out.println("Private Key "+d);
		String msg = "HELLOIERYOP";
		BigInteger message=BigInteger.valueOf(convert(msg));
		System.out.println("Message data = "+msg+" "+message);
		c = message.modPow(e,n);
		System.out.println("\nEncrypted data = "+c);
		BigInteger message1 = c.modPow(d,n);
		System.out.println("\nOriginal Message Sent = "+ message1);
	}
}