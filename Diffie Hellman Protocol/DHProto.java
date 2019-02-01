import java.util.*;
import java.math.*;
class DHProto
{
    static int bitLength=1024;
    BigInteger l;
    static BigInteger p,a,shared;
    private BigInteger k;

    DHProto(int x)
    {
        k=BigInteger.valueOf(x);
        l=modexp(a, k, p);
    }

    static void shared_key(DHProto m,DHProto n)
    {
        shared=modexp(a,m.k.multiply(n.k),p);
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

    static void generate_prime()
    {
        Random rnd=new Random();
        p=BigInteger.probablePrime(bitLength, rnd);
        a=new BigInteger(256, rnd);
    }

    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter secret key");
        int a=sc.nextInt();
        int b=sc.nextInt();
        sc.close();
        DHProto alice=new DHProto(a);
        DHProto bob=new DHProto(b);
        shared_key(alice, bob);
        //shared=modexp(a,alice.k.multiply(bob.k),p);
    }

}