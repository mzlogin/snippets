package datastructure;

/**
 * Created by mazhuang on 2017/2/8.
 */
public class CircularBufferTest {
    public static void main(String[] args) {
        new CircularBufferTest().test();
    }

    private CircularBuffer mBuffer;
    private static final int FRAME_SIZE = 5;
    private Object mSignal = new Object();

    private void test() {
        mBuffer = new CircularBuffer(FRAME_SIZE * 3);
        new WriteThread().start();
        new ReadThread().start();
    }

    class ReadThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[FRAME_SIZE];
            while (true) {
                int deq = 0;
                while (deq < FRAME_SIZE) {
                    int tmp = mBuffer.deqData(buffer, FRAME_SIZE - deq, deq);
                    if (tmp == 0) {
                        synchronized (mSignal) {
                            try {
                                mSignal.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    deq += tmp;
                }

                System.out.println("dequeue data: " + deq);
            }
        }
    }

    class WriteThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[FRAME_SIZE];
            while (true) {
                int enq = 0;
                while (enq < FRAME_SIZE) {
                    int tmp = mBuffer.enqData(buffer, FRAME_SIZE - enq, enq);
                    if (tmp == 0) {
                        synchronized (mSignal) {
                            mSignal.notify();
                        }
                        Thread.yield();
                        continue;
                    }
                    enq += tmp;
                }
                System.out.println("enqueue data: " + enq);
            }
        }
    }
}
