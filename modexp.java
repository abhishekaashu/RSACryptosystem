import java.util.*;

class ModExp
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int res=1;
        int x=sc.nextInt();
        int y=sc.nextInt();
        int p=sc.nextInt();

        while(y>0)
        {
            if(y%2==1)//y is odd
            res=(res*x)%p;
            y=y>>1;//y=y/2
            x=(x*x)%p;
        }
        sc.close();
        System.out.println(res);
    }
}