package thread;

import com.sun.corba.se.impl.orbutil.ObjectUtility;

import javax.management.modelmbean.ModelMBean;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @ClassName MyThreadPool
 * @Author Administrator
 * @date 2020.03.31 13:34
 */
public class MyThreadPool {

    public static void main(String[] args) {
        Integer[] arr = {2,2,3,4};




        Integer i01 = 59;
        int i02 = 59;
        Integer i03 =Integer.valueOf(59);
        Integer i04 = new Integer(59);
        System.out.println(i04 == i03);
    }

}