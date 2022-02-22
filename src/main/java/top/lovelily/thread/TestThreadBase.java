package top.lovelily.thread;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

/**
 * Desc: TestThreadBase
 * Author: xuhe
 * Date: 2018/10/13 下午3:43
 * Version: 1.0
 */
public class TestThreadBase {
    private static int start = 0;
    // 状态码
    private static Integer state = new Integer(1);

    // 奇数
    public static void printOdd() throws InterruptedException {
        synchronized (state) { // 拥有对象监视器
            while (start < 100) {
                if (start % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + ": " + start);
                    start ++;
                   // Thread.sleep(1000l);
                    state.notify(); // 唤醒所有等待在该对象monitor的线程
                } else {
                    state.wait(); // 当前线程等待，并释放对象monitor，直到另一个线程拿到对象monitor后调用notify() 或notifyAll()
                }
            }

        }

    }

    // 偶数
    public static void printEven() throws InterruptedException {
        synchronized (state) {
            while (start < 100) {
                if (start % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + start);
                    start ++;
                    // sleep只是让出CPU，并不释放锁（monitor）
                    // Thread.sleep(1000); // TIMED_WAITING， Odd线程 BLOCKED
                    state.notify();
                } else {
                   // while (true)
                      state.wait();  // WAITING
                }
            }

        }

    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printEven();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printOdd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("Event");
        t2.setName("Odd");
        t1.start();
        t2.start();
        System.out.println(t1.getState());
        t1.join();
        t2.join();
        Thread.yield(); // 让出cpu(核)，todo: 单核cpu时无法再获取cpu，所以下面代码不再执行。
        Thread.currentThread().yield();
        System.out.println("MAIN");

        /**
         *             WAITING(wait(), join(), LockSupport.park)
         *                 ^
         *                 |
         *                 |
         *        start()  |     wait(long),join(long),sleep(long),parkNanos()             finished/interruptException
         *   NEW -----> RUNNABLE-----------------------------------------------> TIMED_WAITING ------------------------> TERMINATED
         *                 |
         *                 |wait()/synchronized
         *                 V
         *             BLOCKED(waiting for monitor)
         *
         *
         * 异常终止：
         *  interrupt():
         *          1. interruptException
         *          2. set interrupt state(isInterrupted()=true), whether should be terminated depends on you
         *
         */

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(1000);  // TIMED_WAITING
                    //while (true) {
                        System.out.println("线程在执行。。。。。。" + Thread.currentThread().getState());
                        System.out.println(Thread.currentThread().isInterrupted());
                        Thread.interrupted();
                  //  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
            }
        });
        thread.setName("TEST-THREAD");

        Map<String, Thread> threadPool = new HashMap<>();
        threadPool.put("thread-1", thread);

        System.out.println("1. " + thread.getState());  // NEW
        thread.start(); // RUNNABLE
        System.out.println("2. " + thread.getState()); //  TIMED_WAITING
         Thread.sleep(2000l); // wait the thread run
       // thread.interrupt();
        System.out.println("3. "  + thread.getState()); // TERMINATED

        System.out.println(thread.isAlive()); // 线程已死（终止）

//      线程终止后，不能重启
//        if (!thread.isAlive()) {
//            System.out.println("线程已死，重新启动");
//            thread.start();
//        }

        System.out.println(threadPool.get("thread-1"));

        // 线程不会被回收，GC-ROOT
        System.gc();
        System.runFinalization();
        System.gc();
        System.out.println(thread);

        Thread.currentThread().interrupt();
//        Thread.currentThread().interrupt();

        Thread baseThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("线程睡眠了");
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park(); // Lock里等待锁的线程通过此方法阻塞  WAITING
                System.out.println("线程醒了： " + Thread.currentThread().getState());
            }
        });
        System.out.println(baseThread.getState()); // NEW
        baseThread.start();
        System.out.println(baseThread.getState()); // RUNNABLE
        Thread.sleep(2000l);
        System.out.println(baseThread.getState()); // WAITING

        LockSupport.unpark(baseThread);
        Thread.sleep(1000l);
        System.out.println(baseThread.getState());

    }
}
