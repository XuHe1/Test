package top.lovelily.wheel;

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
        lock.lock();
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




    public static void main(String[] args) {
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

               //while (true) {
                    for (int i = 0; i < 20; i++) {
                        blockedQueue.put(i);
                    }
                }

           // }
        });
        producer.setName("Producer");
        /**
         *
         * "Consumer" #10 prio=5 os_prio=31 tid=0x00007f8019134000 nid=0x4703 waiting on condition [0x000070000aa02000]
         *    java.lang.Thread.State: WAITING (parking)
         *
         */

        producer.start();
        consumer.start();

    }
}
