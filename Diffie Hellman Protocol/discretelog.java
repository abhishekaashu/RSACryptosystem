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

    public static BigInteger sqroot(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for(;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }
      
    // Function to calculate k for given a, b, m  
    static BigInteger discreteLogarithm(BigInteger a, BigInteger b, BigInteger m) 
    {  
      
        //BigInteger n = m.sqrt();
        //int n = (int) (Math.sqrt (m) + 1);
        try {
            
        
        BigInteger n=sqroot(m);
        n=n.add(BigInteger.ONE);
        BigInteger[] value=new BigInteger[m.intValue()];  
        // Store all values of a^(n*i) of LHS  
        for (BigInteger i = n; i.compareTo(BigInteger.ONE)>= 0;i=i.subtract(BigInteger.ONE))
        {  
            value[modexp(a, i.multiply(n), m).intValue()] = i;
        }
        for (BigInteger j = BigInteger.ZERO; j.compareTo(n)<0; j=j.add(BigInteger.ONE))  
        {  
            // Calculate (a ^ j) * b and check  
            // for collision  
            BigInteger cur = (modexp(a, j, m).multiply(b)).mod(m);

            // If collision occurs i.e., LHS = RHS  
            if ((value[cur.intValue()]).compareTo(BigInteger.ZERO)>0)  
            {  
                BigInteger ans = (value[cur.intValue()].multiply(n)).subtract(j);  
                //Check whether ans lies below m or not  
                if (ans.compareTo(m)<0)  
                    return ans;
            }  
        }  
    } catch (Exception e) {
        System.out.println("No error");
    }
        return BigInteger.valueOf(-1); 
     
}  
} 