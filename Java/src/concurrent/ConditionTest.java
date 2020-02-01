package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mazhuang on 2018/3/5.
 */
public class ConditionTest {

    static int i = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition isEmpty = lock.newCondition();
        Condition isFull = lock.newCondition();

        final int maxBufSize = 5;
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(maxBufSize);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );

        Runnable producerRunnable = new Runnable() {

            @Override
            public void run() {
                long threadId = Thread.currentThread().getId();
                // producer
                while (true) {
                    lock.lock();
                    if (queue.size() >= maxBufSize) {
                        try {
                            System.out.println(threadId + " is full, wait");
                            isFull.signalAll();
                            isEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        queue.add(i);
                        System.out.println(threadId + " produce " + i);
                        i++;
                    }
                    lock.unlock();
                }
            }
        };

        Runnable consumerRunnable = new Runnable() {
            @Override
            public void run() {
                long threadId = Thread.currentThread().getId();
                // consumer
                while (true) {
                    lock.lock();
                    if (queue.isEmpty()) {
                        System.out.println(threadId + " is empty, wait");
                        try {
                            isEmpty.signalAll();
                            isFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Integer i = queue.take();
                            System.out.println(threadId + " consume " + i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.unlock();
                }
            }
        };

        executor.execute(producerRunnable);
        executor.execute(producerRunnable);
        executor.execute(producerRunnable);
        executor.execute(producerRunnable);
        executor.execute(producerRunnable);

        executor.execute(consumerRunnable);
        executor.execute(consumerRunnable);
        executor.execute(consumerRunnable);
        executor.execute(consumerRunnable);
        executor.execute(consumerRunnable);
    }
}
