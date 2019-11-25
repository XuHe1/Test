package top.lovelily.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author h.xu
 * @create 2017-11-20 下午2:19
 **/

public class TestVolatile {
    public static volatile int race = 0;
    public static volatile int i = 0;
    public static volatile int j = 0;
    public static void increase() {
        race ++;
    }
    public static void increment() {
     // 两行代码没有强依赖关系， 因此会出现指令重排， 可能出现 j > i
        i ++;
        j ++;
    }

    public static void get() {
        if (j > i) {
            // 即使加了volatile， 也可能出现 j > i, 不是原子操作
            System.out.println("i="+ i + ", j=" + j);
        }

    }

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Integer.MAX_VALUE; i ++) {
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Integer.MAX_VALUE; i ++) {
                    get();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        for (int i = 0; i < 1000; i ++) {

          Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   // increase();
                   // increment();
                    get();
                }
            });
          thread.setName("custom-thread-" + i);
          thread.start();
        }


       latch.countDown();

//        while (Thread.activeCount() > 1) {
//            Thread.yield();
//        }


        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // race ++ 非原子操作， 所以值可能小于1000
        System.out.println(race);
    }
}
