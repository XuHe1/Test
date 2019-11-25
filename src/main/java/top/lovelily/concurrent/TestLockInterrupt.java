package top.lovelily.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Desc: TestLockInterrupt
 * Author: xuhe
 * Date: 2019/9/27 4:32 PM
 * Version: 1.0
 *
 * 可中断响应
 *
 */
public class TestLockInterrupt {
    private Lock lock = new ReentrantLock();

    private int count = 0;

    private int incrementAndGetCount() {
        try {
            // 想要能够响应中断，需使用 lock.lockInterruptibly(); 而不能是 lock.lock();
            lock.lockInterruptibly();
            lock.lock();

            System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
            return count++;
        } catch (Exception e) {
            return 0;
        } finally {
            // 并没有在 finally 中释放锁
            // lock.unlock();
        }
    }

    public static void main(String[] args) {
        TestLockInterrupt test = new TestLockInterrupt();

        Thread t1 = new Thread() {
            public void run() {
                test.incrementAndGetCount();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                test.incrementAndGetCount();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };

        t1.start();
        t2.start();
        t2.interrupt();
    }

}
