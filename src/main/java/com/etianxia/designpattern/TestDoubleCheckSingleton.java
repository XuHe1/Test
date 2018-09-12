package com.etianxia.designpattern;

import com.sun.xml.internal.bind.api.TypeReference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author h.xu
 * @create 2017-11-17 下午6:25
 **/

public class TestDoubleCheckSingleton{
    /**
     * 1. volatile 保证线程之间对变量的修改，立即可见， 但不保证原子性。
     * 2. volatile使用场景：
     *          a. 状态标记量
     *          b. 双检锁单例（Double Check Lock）
     * 3. 自增自减操作不是原子操作（包括读取变量的原始值、进行加1操作、写入工作内存），如需要保证原子性使用 AtomInteger AtomLong (使用场景： 计数器)
     */

    /**
     *  读写互斥
     *  写读互斥
     *  写写互斥
     *  读读不互斥
     */

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static  volatile TestDoubleCheckSingleton INSTANCE;

    private TestDoubleCheckSingleton(){}

    public static TestDoubleCheckSingleton getInstance(){
        if(INSTANCE == null){
            synchronized(TestDoubleCheckSingleton.class){
                //double checking Singleton instance
                if(INSTANCE == null){
                    /** volatile 防止指令重排序（设置内存屏障）
                     *  1. memory = allocate();
                     *  2. init(memory);
                     *  3. instance = memory;
                     *  2依赖于1，3不依赖于2，可能发生指令重排序： 1 3 2， 执行3未执行2，另一条线程调用getInstance()就获得了个未被初始化的对象
                     */
                    System.out.println("实例化");
                    INSTANCE = new TestDoubleCheckSingleton();
                }
            }
        }
        return INSTANCE;
    }


    public static TestDoubleCheckSingleton getInstance1(){
        if(INSTANCE == null){
            // 无double check 还是会多次实例
            synchronized(TestDoubleCheckSingleton.class){
                    System.out.println("实例化");
                    INSTANCE = new TestDoubleCheckSingleton();
            }
        }
        return INSTANCE;
    }


    public  TestDoubleCheckSingleton getInstance1(TypeReference typeReference) {

        /**
         * 1. 缓存框架：读写锁(使用见源码) + Double check
         * 2. CountDownLatch 模拟多线程 缓存穿透
         * 3. Snowflake
         * 4. CountDownLatch
         *
         */

        readWriteLock.readLock().lock();
        if(INSTANCE == null){
            readWriteLock.writeLock().lock();
            INSTANCE = new TestDoubleCheckSingleton();
            System.out.println("=====实例化完成");
            readWriteLock.writeLock().unlock();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        int a = 1;
        int b = a;
        /**
         *   todo: 汇编语言
         *   non-atomic: javap -c  classfile
         *        4: iload_1
         *        5: iconst_2
         *        6: iadd
         *        7: istore_1
         *
         */

        a = a + 2;

        AtomicInteger count = new AtomicInteger(0);
        count.getAndIncrement();
        System.out.println(count.intValue());

        TestDoubleCheckSingleton.getInstance();

    }
}