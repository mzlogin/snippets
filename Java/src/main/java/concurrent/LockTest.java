package concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LockTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                3,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1)
        );

        executor.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println("111");
                }
            }
        });

        executor.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println("222");
                }
            }
        });

        executor.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println("333");
                }
            }
        });
    }
}
