package top.lovelily.concurrent;


import java.util.concurrent.CountDownLatch;

/**
 * @author h.xu
 * @create 2017-11-20 下午2:19
 **/

public class TestVolatile {
    // 没有volatile，类成员变量和对象成员变量都无法保证可见性
      //  boolean flag = true;
     private static boolean flag = true;
     //private volatile  boolean flag = true;

    static int count = 0;

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

    void startJob() {
        System.out.println("m start");
        while (this.flag) {
            // 空循环线程，或者计算密集型任务， cpu不会释放cpu
            count ++;
            // 2. 不能有IO操作，会引起上下文切换，重新读取flag
            //System.out.println(Thread.currentThread().getName() + ": true" );

        }
        // 循环体空， 这行代码只有在volatile情况下执行，
        // 循环体不为空， 这行代码一定会执行？ todo：Why?
        System.out.println("m end");

    }

    void shutDownJob() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.flag = false;


    }
    // non-volatile，cpu寄存器数据输入主存时间不确定, 4ms
    // 13591864226402
    // 13591868276250

    //   volatile   1 ms
    //   13660887087509
    //   13660886901438
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        TestVolatile test = new TestVolatile();

       Thread t1 =  new Thread(test::startJob,  "Cycle-Task");

       Thread t2 = new Thread(test::shutDownJob, "Trigger");

        t1.start();

        t2.start();
//        t1.join();
//        t2.join();


//        for (int i = 0; i < 1000; i ++) {
//
//          Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        latch.await();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                   // increase();
//                   // increment();
//                    get();
//                }
//            });
//          thread.setName("custom-thread-" + i);
//          thread.start();
//        }
//
//
//       latch.countDown();
//
////        while (Thread.activeCount() > 1) {
////            Thread.yield();
////        }
//
//
//        try {
//            Thread.sleep(10000l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // race ++ 非原子操作， 所以值可能小于1000
//        System.out.println(race);
    }
}
