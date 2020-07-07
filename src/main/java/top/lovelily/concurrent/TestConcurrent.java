package top.lovelily.concurrent;

import top.lovelily.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author h.xu
 * @create 2017-11-20 上午11:13
 * todo: 了解并发底层实现原理， 锁、CAS、AQS
 * 1. CAS底层实现是 cpu的cmpxchg指令，由内核调用
 * 2. AQS，state变量抽象锁、队列
 * 3. 锁： 可中断，不可中断，如何实现的 todo：？
 **/

public class TestConcurrent {

    // CAS
 //   private static final Unsafe unsafe = Unsafe.getUnsafe();

    int i = 0;

      boolean  stop = true;

    //private volatile int inc = 0;

    // 同ConcurrentLinkedQueue实现原理 CAS  Compare And Swap
    // todo: what is CAS? 从底层内存上面的原子命令，AQS AbstractQueuedSynchronizer
    // 利用JNI(java native interface),通过C调用cpu的CAS指令
    // It compares the contents of a memory location with a given value and, only if they are the same, modifies the contents of that memory location to a new given value. This is done as a single atomic operatio
    private AtomicInteger inc = new AtomicInteger();
    //  链表
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    // 链表
    LinkedList<User> userLinkedList = new LinkedList<>();

    // 自旋锁
    ConcurrentLinkedQueue<User> linkedQueue = new ConcurrentLinkedQueue<>();

    // 链表，分段锁，java8已经弃用，重新使用了synchronized
    ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap();

    HashMap<String, String> hashMap = new HashMap<String, String>();//



    public void increase() {
       // inc++; // 非原子操作 包括读取变量的原始值、进行加1操作、写入工作内存
        inc.getAndIncrement();
    }


    public void doSomething() {
        /**
         *        0: aload_0
         *        1: getfield      #2                  // Field stop:Z
         *        4: ifne          10
         *        7: goto          0
         *       10: getstatic     #22                 // Field java/lang/System.out:Ljava/io/PrintStream;
         */

        while (stop) {  // 基本类型的read是原子操作，
           //System.out.println("hello");
            i ++;

        }
        System.out.println(System.currentTimeMillis());
    }

    public void stop() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = false; // 基本类型的assign是原子操作
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

        // HashMap 会按key进行排序（与插入时顺序不一致）， LinkedHashMap通过双向链表保存插入顺序
        test.hashMap.put("name", "Tom");
        test.hashMap.put("e", "e");
        test.hashMap.put("d", "d");
        test.hashMap.put("a", "a");
        test.hashMap.put("b", "b");
        test.hashMap.put("c", "c");
        Iterator iterator = test.hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }



//
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.doSomething();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.stop();
            }
        });

        thread1.start();
        thread2.start();

    }
}
