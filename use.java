import java.util.*; 
import java.lang.*; 
  
class GFG 
{ 
  
    // Returns modulo inverse of a with 
    // respect to m using extended Euclid 
    // Algorithm Assumption: a and m are 
    // coprimes, i.e., gcd(a, m) = 1 
    static int modInverse(int a, int m) 
    { 
        int m0 = m; 
        int y = 0, x = 1; 
  
        if (m == 1) 
            return 0; 
  
        while (a > 1) 
        { 
            // q is quotient 
            int q = a / m; 
  
            int t = m; 
  
            // m is remainder now, process 
            // same as Euclid's algo 
            m = a % m; 
            a = t; 
            t = y; 
  
            // Update x and y 
            y = x - q * y; 
            x = t; 
        } 
  
        // Make x positive 
        if (x < 0) 
            x += m0; 
  
        return x; 
    } 
  
    // Driver program to test above function 
    public static void main(String args[]) 
    { 
        int a = 7, m = 11; 
  
        System.out.println("Modular multiplicative "+ 
                           "inverse is " + modInverse(a, m)); 
    } 
} 