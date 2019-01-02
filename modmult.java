import java.util.*;

class ModMult
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int res=0;
        int x=sc.nextInt();
        int y=sc.nextInt();
        int p=sc.nextInt();
        x=x%p;
        while(y>0)
        {
            if(y%2==1)//y is odd
            res=(res+x)%p;
            x=(2*x)%p;
            y=y>>1;//y=y/2
        }
        sc.close();
        System.out.println(res);
    }
}