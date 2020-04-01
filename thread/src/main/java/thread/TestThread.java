package thread;

/**
 * @Description
 * @ClassName TestThread
 * @Author Administrator
 * @date 2020.03.30 15:43
 */
public class TestThread extends Thread {
    @Override
    public void run() {
        int number = 10000;


        for (int i = 0; i < number; i++) {

            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Integer number = 199;
        new TestThread().start();
        for (int i = 0; i < number; i++) {
            System.out.println("11111111111");
        }
    }
}
