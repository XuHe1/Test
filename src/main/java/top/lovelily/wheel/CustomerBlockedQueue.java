package top.lovelily.wheel;

import org.openjdk.jol.info.ClassLayout;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Desc: CustomerBlockedQueue
 * Author: xuhe
 * Date: 2019/6/23 10:33 PM
 * Version: 1.0
 */
public class CustomerBlockedQueue<E> {
    //
    private LinkedList<E> linkedList= new LinkedList();

    // for all access
    private final ReentrantLock lock = new ReentrantLock();

    // waiting take
    private final Condition notEmpty = lock.newCondition();

    // waiting put
    private final Condition notFull = lock.newCondition();

    private volatile int maxSize;

    public CustomerBlockedQueue(int size) {
        maxSize = size;
    }

    // ReentrantLock + Condition
    public E take() {
        ReentrantLock lock = this.lock;
        lock.lock();
        System.out.println(lock.getHoldCount());

        try {
           while (linkedList.size() == 0) {
               notEmpty.await();
           }
            notFull.signal();
            return linkedList.removeFirst();
        } catch (InterruptedException e) {
            return null;
        } finally {
            lock.unlock();
        }

    }

    // 如果队列满了，线程阻塞直到返回true
    public boolean put(E e) {
        ReentrantLock lock = this.lock;
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        lock.lock();
        System.out.println(Thread.currentThread().getState());
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        try {
            while (linkedList.size() >= maxSize) {  // while ?
                notFull.await(); // cause current thread to wait, release monitor
            }
            linkedList.addLast(e);
            notEmpty.signal();
            return true;
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }

    }

    private void getLockState() {
        // 的确在调用了await方法后，释放了锁
        System.out.println(this.lock.getHoldCount());
    }



    public static void main(String[] args) throws InterruptedException {
        CustomerBlockedQueue<Integer> blockedQueue = new CustomerBlockedQueue(10);
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(blockedQueue.take());
                }

            }
        });
        consumer.setName("Consumer");

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                    for (int i = 0; i < 20; i++) {
                        System.out.println(blockedQueue.put(i));
                    }
                }

        });
        producer.setName("Producer");
        /**
         *
         * "Consumer" #10 prio=5 os_prio=31 tid=0x00007f8019134000 nid=0x4703 waiting on condition [0x000070000aa02000]
         *    java.lang.Thread.State: WAITING (parking)
         *
         */

       // producer.start();
        consumer.start();

        Thread.sleep(2000L);
        new Thread(new Runnable() {
            @Override
            public void run() {
                blockedQueue.getLockState();
            }
        }).start();

    }
}
