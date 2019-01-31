import java.math.BigInteger;

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
    static BigInteger discreteLogarithm(BigInteger a,BigInteger b,BigInteger m) {  
      
        BigInteger n =m.sqrt().add(BigInteger.ONE);  
      
        BigInteger[] value=new BigInteger[m.intValue()];  
      
        // Store all values of a^(n*i) of LHS  
        for (BigInteger i = n; i.compareTo(BigInteger.ONE)>=0;i=i.subtract(BigInteger.ONE))  
            value[modexp(a, i.multiply(n), m).intValue()] = i;  
      
        for (BigInteger j = BigInteger.ZERO; j.compareTo(n)<0; j=j.add(BigInteger.ONE))  
        {  
            // Calculate (a ^ j) * b and check  
            // for collision  
            int cur = ((modexp(a, j, m).multiply(b)).mod(m)).intValue();  
      
            // If collision occurs i.e., LHS = RHS  
            if (value[cur].compareTo(BigInteger.ZERO)>0)  
            {  
                BigInteger ans = value[cur].multiply(n).subtract(j);  
                // Check whether ans lies below m or not  
                if (ans.compareTo(m)<0)  
                    return ans;  
            }  
        }  
        return BigInteger.valueOf(-1);  
    }  
      
    // Driver code  
    public static void main(String[] args)  
    {  
        BigInteger a=BigInteger.valueOf(2);
        BigInteger b=BigInteger.valueOf(3);
        BigInteger m=BigInteger.valueOf(5);

        System.out.println(discreteLogarithm(a, b, m));  
      
        a = BigInteger.valueOf(3); 
        b = BigInteger.valueOf(7); 
        m = BigInteger.valueOf(11);  
        System.out.println(discreteLogarithm(a, b, m));  
    }  
}