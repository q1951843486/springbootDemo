package thread;

/**
 * @Description
 * @ClassName MyThread
 * @Author Administrator
 * @date 2020.03.31 10:18
 */
public class MyThread implements Runnable{
    public void run() {
        int number = 100;
        for (int i = 0; i <number ; i++) {
            System.out.println(i);
        }
    }


    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.start();
        int number = 100;
        for (int i = 0; i <number; i++) {
            System.out.println("111111111111111");
        }
    }
}
