package top.lovelily.concurrent;

/**
 * 对象锁：包含方法锁、对象锁，作用于某一具体对象
 * 类锁：作用于类，作用于所有对象
 *
 * 与Lock区别：https://docs.oracle.com/javase/1.5.0/docs/guide/concurrency/overview.html
 * 实现原理：monitor_enter monitor_exit， Lock基于AQS
 * 功能： 不能手动unlock, 一旦获取锁， 必须等待方法体直行完（包括异常退出）
 * 可重入：锁基于线程分配，而不是基于方法分配。synchronized可重入
 * 可中断：synchronized导致线程无限等待， lock.lockInterruptibly()可以
 * 公平：synchronize非公平， lock默认非公平， 可以通过参数变成公平锁
 * 粒度：Lock分为读锁， 写锁
 *
 * 悲观锁乐观锁
 *
 *
 * Desc: TestSynchronized
 * Author: xuhe
 * Date: 2018/9/12 下午12:41
 * Version: 1.0
 */
public class TestSynchronized {
    private static int count = 0;

    // 方法锁(对象锁)
    public synchronized void increase() {
        count ++;
    }

    // 类锁
    public synchronized static int getCount() {
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
    public static void main(String[] args) {
        for (int i = 0 ; i < 1000; i ++) {
            getCount();
            resetCount();
            System.out.println(count);
        }

      new Thread(new Runnable() {
            @Override
            public void run() {
                getCount();
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }

}
