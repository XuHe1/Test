package top.lovelily.concurrent;

/**
 * 对象锁：包含方法锁、对象锁，作用于某一具体对象
 * 类锁：作用于类，作用于所有对象
 *
 * 与Lock区别：https://docs.oracle.com/javase/1.5.0/docs/guide/concurrency/overview.html
 * 实现原理：monitor_enter monitor_exit， Lock基于AQS （volatile + CAS）
 * 功能： 不能手动unlock, 一旦获取锁， 必须等待方法体直行完（包括异常退出）
 * 可重入：锁基于线程分配，而不是基于方法分配。synchronized可重入
 * 可中断：synchronized导致线程无限等待， lock.lockInterruptibly()可以
 * 公平：synchronize非公平， lock默认非公平， 可以通过参数变成公平锁
 * 粒度：Lock分为读锁， 写锁
 *
 * 悲观锁乐观锁
 *
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
 * synchronized语义：
 * 1. 多线程调用同一对象的同一方法，是串行的；
 * 2. synchronized 保证了可见性；
 *
 * Desc: TestSynchronized
 * Author: xuhe
 * Date: 2018/9/12 下午12:41
 * Version: 1.0
 */
public class TestSynchronized {
    private static int count = 0;

    // 方法锁(对象锁), 多线程调用同一对象：同一把锁
    public synchronized void increase() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        count ++;
    }

    // 类锁
    public synchronized static int getCount() {
        System.out.println(Thread.currentThread().getName());
        return count;
    }

    public int getCount1() {
        // 类锁
        synchronized (TestSynchronized.class) {
            return count;
        }
    }

    public int getCount2() {
        // 对象锁
        synchronized (this) {
            return count;
        }
    }

    public static void resetCount() {
        count = 1;
    }
    public static void main(String[] args) throws InterruptedException {
        TestSynchronized testSynchronized = new TestSynchronized();

        // t1 t2共用一把锁
      Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i< 10; i++) {
//                    try {
//                        Thread.sleep(1000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                   // getCount(); // 可重入， 一个线程可多次加锁
                    testSynchronized.increase();
                }

            }
        });

      Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i< 10; i++) {
                    //getCount();
                    testSynchronized.increase();
                }
            }
        });
      t1.start();
      t2.start();
      t1.join();
      t2.join();
    }

}
