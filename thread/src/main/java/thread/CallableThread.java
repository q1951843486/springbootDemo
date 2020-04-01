package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description
 * @ClassName CallableThread
 * @Author Administrator
 * @date 2020.03.31 10:23
 */
public class CallableThread implements Callable {
    @Override
    public Object call() throws Exception {

        return "hello world";
    }

    public static void main(String[] args) {
        //使用线程池
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future> list = new ArrayList<Future>(16);
        int num = 16;
        for (int i = 0; i <num ; i++) {
            Callable c = new CallableThread();
            Future f = executorService.submit(c);
            list.add(f);
        }
        executorService.shutdown();
        list.forEach(f -> {
            try {
                System.out.println(f.get().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
