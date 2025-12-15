package top.lovelily.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicLong
 */
public class TestLongAdder {
    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i <100 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("开始执行" + System.currentTimeMillis());
                    adder.increment();
                }
            }).start();
            latch.countDown();
        }

        try {
            Thread.currentThread().sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("计数器："+ adder.sum());
    }
}
