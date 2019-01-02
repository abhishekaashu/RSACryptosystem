import java.util.*;

class Euclid
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int x=sc.nextInt();
        int y=sc.nextInt();
        int z=gcd(x,y);
        System.out.println(z);
    }

    static int gcd(int a,int b)
    {
        if(a==0)
        return b;
        else if(b==0)
        return a;
        else if(a>b)
        return gcd(a%b,b);
        else
        return gcd(a,b%a);
    }
}