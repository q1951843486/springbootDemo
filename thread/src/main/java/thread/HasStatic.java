package thread;

/**
 * @Description
 * @ClassName HasStatic
 * @Author Administrator
 * @date 2020.04.01 13:46
 */
public class HasStatic{
    private static int x=100;
    public static void main(String args[]){
        HasStatic hs1=new HasStatic();
        hs1.x++;
        HasStatic  hs2=new HasStatic();
        hs2.x++;
        hs1=new HasStatic();
        hs1.x++;
        HasStatic.x--;
        System.out.println("x="+x);
    }
}