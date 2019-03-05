import java.math.*;

class MillerTest
{
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
    
    static BigInteger modexp(BigInteger x,BigInteger y,BigInteger p)
    {
        BigInteger res=BigInteger.ONE;
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
}