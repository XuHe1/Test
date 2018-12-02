package top.lovelily.designpattern;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author h.xu
 * @create 2017-11-17 下午6:25
 **/

public class TestDoubleCheckSingleton{

    private  int a;
    private static AtomicBoolean locked = new AtomicBoolean(false);

    /**
     * CAS 保证原子操作
     * @return
     */
    public static boolean lock() {
        return locked.compareAndSet(false, true);
    }


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

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static  volatile TestDoubleCheckSingleton INSTANCE;

    private static  AtomicReference<TestDoubleCheckSingleton> atomicReference = new AtomicReference<>(null);

    public TestDoubleCheckSingleton(){
        System.out.println("new TestDoubleCheckSingleton()");

    }


    /**
     *  虚拟机指令
     *       0: getstatic     #5                  // Field INSTANCE:Lcom/etianxia/designpattern/TestDoubleCheckSingleton;
     *        3: ifnonnull     45
     *        6: ldc           #6                  // class com/etianxia/designpattern/TestDoubleCheckSingleton
     *        8: dup
     *        9: astore_0
     *       10: monitorenter
     *       11: getstatic     #5                  // Field INSTANCE:Lcom/etianxia/designpattern/TestDoubleCheckSingleton;
     *       14: ifnonnull     35
     *       17: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *       20: ldc           #8                  // String 实例化
     *       22: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *       25: new           #6                  // class com/etianxia/designpattern/TestDoubleCheckSingleton
     *       28: dup
     *       29: invokespecial #10                 // Method "<init>":()V
     *       32: putstatic     #5                  // Field INSTANCE:Lcom/etianxia/designpattern/TestDoubleCheckSingleton;
     *       35: aload_0
     *       36: monitorexit                       // todo: 内存屏障 volatile
     *       37: goto          45
     *       40: astore_1
     *       41: aload_0
     *       42: monitorexit
     *       43: aload_1
     *       44: athrow
     *       45: getstatic     #5                  // Field INSTANCE:Lcom/etianxia/designpattern/TestDoubleCheckSingleton;
     *       48: areturn
     *
     *
     *
     *
     */

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


    /**
     *
     *        0: getstatic     #5                  // Field INSTANCE:Lcom/etianxia/designpattern/TestDoubleCheckSingleton;
     *        3: ifnonnull     39
     *        6: ldc           #6                  // class com/etianxia/designpattern/TestDoubleCheckSingleton
     *        8: dup
     *        9: astore_0
     *       10: monitorenter
     *       11: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *       14: ldc           #8                  // String 实例化
     *       16: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *       19: new           #6                  // class com/etianxia/designpattern/TestDoubleCheckSingleton
     *       22: dup
     *       23: invokespecial #10                 // Method "<init>":()V
     *       26: putstatic     #5                  // Field INSTANCE:Lcom/etianxia/designpattern/TestDoubleCheckSingleton;
     *       29: aload_0
     *       30: monitorexit
     *       31: goto          39
     *       34: astore_1
     *       35: aload_0
     *       36: monitorexit
     *       37: aload_1
     *       38: athrow
     *       39: getstatic     #5                  // Field INSTANCE:Lcom/etianxia/designpattern/TestDoubleCheckSingleton;
     *       42: areturn
     *
     *
     * @return
     */
    public static TestDoubleCheckSingleton getInstance1(){
        if(INSTANCE == null){
            // 无double check 还是会多次实例,高并发情况下，线程都执行了if语句后到此，排队进锁实例
            synchronized(TestDoubleCheckSingleton.class){
                    System.out.println("实例化");
                    INSTANCE = new TestDoubleCheckSingleton();
            }
        }
        return INSTANCE;
    }



    /**
     * todo:
     *       1. TypeReference
     *       2. readLock 多余？
     * @return
     */

    public static TestDoubleCheckSingleton getInstance2() {

        /**
         * 1. 缓存框架：读写锁(使用见源码) + Double check
         * 2. CountDownLatch 模拟多线程 缓存穿透
         * 3. Snowflake
         * 4. CountDownLatch
         *
         */

        // readWriteLock.readLock().lock();
        if(INSTANCE == null){
            readWriteLock.writeLock().lock();
            if (INSTANCE == null) {
                INSTANCE = new TestDoubleCheckSingleton();
            }
            System.out.println("=====实例化完成");
            readWriteLock.writeLock().unlock();
        }
        // readWriteLock.readLock().unlock();
        return INSTANCE;
    }


    public static boolean getINSTANCE() {
        // new TestDoubleCheckSingleton() 非原子操作
        return atomicReference.compareAndSet(null, new TestDoubleCheckSingleton());

    }


    @Test
    public void testAssign() {
        /**
         *        0: iconst_1
         *        1: istore_1
         *        2: return
         *
         */
        // a = 1;

        if (a == 1) {

        }
    }


    public static void main(String[] args) {
          int a = 1;
//        System.out.println("hello");
//        int b = a;
//        /**
//         *   javap -c  classfile 对代码进行反汇编
//         *   todo: 汇编语言
//         *   non-atomic:
//         *        4: iload_1
//         *        5: iconst_2
//         *        6: iadd
//         *        7: istore_1
//         *
//         */
//
//        a = a + 2;
//
//        AtomicInteger count = new AtomicInteger(0);
//        count.getAndIncrement();
//        System.out.println(count.intValue());
//
//        TestDoubleCheckSingleton.getInstance();

    }
}