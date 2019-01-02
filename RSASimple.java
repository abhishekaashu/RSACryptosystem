import java.util.*;
import java.math.*;
class RSASimple
{

	static long gcd(long a, long h) 
	{
		long temp;
		while (true)
		{
			temp = a % h;
			if (temp == 0)
				return h;
			a = h;
			h = temp;
		}
	}

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
		long p = 41;
		long q = 79;
		long n = p * q;
		int e = 2;
		long phi = (p - 1) * (q - 1);
		while (e < phi) 
		{
			if (gcd(phi, e) == 1)
				break;
			else
				e++;
		}
		int k = 2;
		System.out.println("Public Key "+n+" "+e);
		long d = (1 + (k * phi)) / e;
		System.out.println("Private Key "+d);
		String msg = "HI";
		int message=convert(msg);
		System.out.println("Message data = "+msg+" "+message);
		long c = (long)Math.pow(message, e);
		c=c%n;
		System.out.println("\nEncrypted data = "+c);
		long m = (long)Math.pow(c, d);
		m = m%n;
		System.out.println("\nOriginal Message Sent = "+ m);
	}
}