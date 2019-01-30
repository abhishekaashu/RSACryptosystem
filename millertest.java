import java.util.*;
import java.math.*;
class test1
{
    static boolean millerTest(BigInteger d, BigInteger n)
    {
        Random rand = new Random();
        BigInteger a = new BigInteger(n.intValue(), rand);
        a=a.add(BigInteger.TWO);
        BigInteger x = modexp(a, d, n); 
        if (x.compareTo(BigInteger.ONE)==0 || x == n.subtract(BigInteger.ONE))
            return true; 
        while (d != n.subtract(BigInteger.ONE)) { 
            x = (x.multiply(x)).mod(n); 
            d=d.multiply(BigInteger.TWO); 
            if (x.compareTo(BigInteger.ONE)==0) 
                return false; 
            if (x ==n.subtract(BigInteger.ONE)) 
                return true; 
        } 
        return false; 
    }
    static boolean isPrime(BigInteger n, int k)
    { 

        if (n.compareTo(BigInteger.ONE)<=0 || n.compareTo(BigInteger.valueOf(4))==0) 
            return false; 
        if (n.compareTo(BigInteger.valueOf(3))<0) 
            return true; 
        BigInteger d= n.subtract(BigInteger.ONE);
        while (d.mod(BigInteger.valueOf(2)).compareTo(BigInteger.TWO)==0) 
            d=d.divide(BigInteger.TWO); 
        for (int i = 0; i < k; i++) 
            if (!millerTest(d, n)) 
                return false; 
        return true; 
    }
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
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        String s1=sc.nextLine();
        BigInteger a=new BigInteger(s1);
        boolean d=isPrime(a,4);
        System.out.println(d);
        sc.close();
    }
}