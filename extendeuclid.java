import java.util.*;

class ExtendEuclid
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int a=sc.nextInt();
        int b=sc.nextInt();
        sc.close();
        int x=1,y=1;
        int z=euclid(a,b,x,y);
        System.out.println(z);
    }

    static int euclid(int a,int b,int x,int y)
    {
        int x1=1,y1=1,gcd;
        if(a==0)
        {
            x=0;
            y=1;
            return b;
        }
        else if(b==0)
        {
            x=1;
            y=0;
            return a;
        }
        else if(a>b)
        {
            gcd=euclid(a%b,b,x1,y1);
            x=y1-(a/b)*x1;
            y=x1;
        }
        
        else
        {
            gcd=euclid(a,b%a,x1,y1);
            x=y1;
            y=x1-(b/a)*y1;
        }
            return gcd;
    }
}