import java.math.*;
class GFG{ 
    /* Iterative Function to calculate (x ^ y)%p in  
    O(log y) */
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
      
    // Function to calculate k for given a, b, m  
    static BigInteger discreteLogarithm(BigInteger a, BigInteger b, BigInteger m) 
    {  
      
        //BigInteger n = m.sqrt();
        BigInteger n=BigInteger.valueOf(3);  
      
        BigInteger[] value=new BigInteger[m.intValue()];  
      
        // Store all values of a^(n*i) of LHS  
        for (BigInteger i = n; i.compareTo(BigInteger.ONE)== 1;i=i.subtract(BigInteger.ONE))  
            value[modexp(a, i.multiply(n), m).intValue()] = i;  
      
        for (BigInteger j = BigInteger.ONE; j.compareTo(n)==-1; j=j.add(BigInteger.ONE))  
        {  
            // Calculate (a ^ j) * b and check  
            // for collision  
            BigInteger cur = (modexp(a, j, m).multiply(b)).mod(m);  
      
            // If collision occurs i.e., LHS = RHS  
            if (value[cur.intValue()].compareTo(BigInteger.ZERO)==1)  
            {  
                BigInteger ans = (value[cur.intValue()].multiply(n)).subtract(j);  
                // Check whether ans lies below m or not  
                if (ans.compareTo(m)==-1)  
                    return ans;  
            }  
        }  
        return BigInteger.valueOf(-1);  
    }  
      
    // Driver code  
    public static void main(String[] args)  
    {  
        int a = 3, b = 7, m = 11;  
        System.out.println(discreteLogarithm(BigInteger.valueOf(a), BigInteger.valueOf(b), BigInteger.valueOf(m)));  
      
        a = 3; 
        b = 7; 
        m = 11;  
        System.out.println(discreteLogarithm(BigInteger.valueOf(a), BigInteger.valueOf(b), BigInteger.valueOf(m)));  
    }  
} 