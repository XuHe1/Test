package top.lovelily.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Desc: TestThreadLocalRandom
 * Author: xuhe
 * Date: 2020/11/24 下午4:55
 * Version: 1.0
 */
public class TestThreadLocalRandom {
    private static CountDownLatch latch = new CountDownLatch(10);
    // Random多线程竞争激烈，效率低下？推荐使用
    private static Random random = new Random(10);
    private static ThreadLocalRandom  localRandom = ThreadLocalRandom.current();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(random.nextInt());
                }
            });
            thread.start();
            latch.countDown();
        }
    }
}
