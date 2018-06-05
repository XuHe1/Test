package com.etianxia.concurrent;

import com.etianxia.User;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author h.xu
 * @create 2017-11-20 上午11:13
 **/

public class TestConcurrent {

    volatile boolean  stop = false;

    //private volatile int inc = 0;

    // 同ConcurrentLinkedQueue实现原理 CAS  Compare And Swap
    // todo: what is CAS? 从底层内存上面的原子命令，
    // 利用JNI(java native interface),通过C调用cpu的CAS指令
    // It compares the contents of a memory location with a given value and, only if they are the same, modifies the contents of that memory location to a new given value. This is done as a single atomic operatio
    private AtomicInteger inc = new AtomicInteger();
    //  链表
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    // 链表
    LinkedList<User> userLinkedList = new LinkedList<>();

    // 自旋锁
    ConcurrentLinkedQueue<User> linkedQueue = new ConcurrentLinkedQueue<>();

    // 链表，分段， 锁
    ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap();

    public void increase() {
       // inc++; // 非原子操作 包括读取变量的原始值、进行加1操作、写入工作内存
        inc.getAndIncrement();
    }


    public void doSomething() {
        while (!stop) {  // 基本类型的read是原子操作，
            System.out.println(System.currentTimeMillis());
        }
    }

    public void stop() {
        stop = true; // 基本类型的asign是原子操作
    }

    public static void main(String[] args) {
        final TestConcurrent test = new TestConcurrent();
        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    {
                        for(int j=0;j<100;j++)
                            test.increase();
                    }
                }
            }).start();
        }

        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();

        System.out.println(test.inc);


//
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                test.doSomething();
//            }
//        });
//
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                test.stop();
//            }
//        });
//
//        thread1.start();
//        thread2.start();

    }
}
