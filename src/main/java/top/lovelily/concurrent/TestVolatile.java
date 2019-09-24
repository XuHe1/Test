package top.lovelily.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author h.xu
 * @create 2017-11-20 下午2:19
 **/

public class TestVolatile {
    public static volatile int race = 0;
    public static void increase() {
        race ++;
    }

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i ++) {
          Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    increase();
                }
            });
          thread.setName("custom-thread-" + i);
          thread.start();

        }

        latch.countDown();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

//        try {
//            Thread.sleep(2000l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // race ++ 非原子操作， 所以值可能小于1000
        System.out.println(race);
    }
}
