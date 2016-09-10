import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

class TestThread implements Runnable {
    public void run() {
        for (int i = 0; i < 100; i ++) {
            System.out.println(Thread.currentThread().getName() + "的i值为:" + i);
        }
    }
}

public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(6);
        pool.submit(new TestThread());
        pool.submit(new TestThread());
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                System.out.println("All is done!");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
