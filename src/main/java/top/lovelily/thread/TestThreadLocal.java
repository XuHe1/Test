package top.lovelily.thread;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Desc: TestThreadLocal， 不同线程数据隔离，同线程多方法间数据共享
 * Author: xuhe
 * Date: 2019/4/29 9:55 PM
 * Version: 1.0
 *
 * 应用：
 *     数据库事务，同一个线程获取的数据库连接是一个
 *
 */
public class TestThreadLocal {

    // 弱引用，防止内存泄漏
    private static ThreadLocal<String> localName = new ThreadLocal<>();

    public TestThreadLocal(String name) {
        localName.set(name);
    }

    private String getString() {
        return Thread.currentThread().getName() + ":" + localName.get();
    }

    private void setString(String string) {
        localName.set(string);
    }

    public void remove() {
        localName.remove();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("对象被回收了！");
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 100; i++) {
            sb.append("（1）静态常量池：即*.class文件中的常量池，在Class文件结构中，最头的4个字节存储魔数，用于确定一个文件是否能被JVM接受，接着4个字节用于存储版本号，前2个为次版本号，后2个主版本号，再接着是用于存放常量的常量池，由于常量的数量是不固定的，所以常量池的入口放置一个U2类型的数据(constant_pool_count)存储常量池容量计数值。\n" +
                    "\n" +
                    "这种常量池占用class文件绝大部分空间，主要用于存放两大类常量：字面量和符号引用量，字面量相当于Java语言层面常量的概念，如文本字符串、基础数据、声明为final的常值等；符号引用则属于编译原理方面的概念，包括了如下三种类型的常量：类和接口的全限定名、字段名称描述符、方法名称描述符。类的加载过程中的链接部分的解析步骤就是把符号引用替换为直接引用，即把那些描述符（名字）替换为能直接定位到字段、方法的引用或句柄（地址）。\n" +
                    "\n" +
                    "（2）运行时常量池：虚拟机会将各个class文件中的常量池载入到运行时常量池中，即编译期间生成的字面量、符号引用，总之就是装载class文件。为什么它叫运行时常量池呢？因为这个常量池在运行时，里面的常量是可以增加的。如：“+”连接字符生成新字符后调用 intern（）方法、生成基础数据的包装类型等等。\n" +
                    "\n" +
                    "（3）字符串常量池 ：字符串常量池可以理解为是分担了部分运行时常量池的工作。加载时，对于class文件的静态常量池，如果是字符串就会被装到字符串常量池中。\n" +
                    "\n" +
                    "（4）整型常量池：Integer，类似字符串常量池。管理-128--127的常量。类似的还有Character、Long等常量池（基本数据类型没有，Double、Float也没有常量池）\n" +
                    "\n" +
                    "总结就是：\n" +
                    "\n" +
                    "class文件有常量池存放这个类的信息，占用了大多数空间。但是运行时所有加载进来的class文件的常量池的东西都要放到运行时常量池，这个运行时常量池还可以在运行时添加常量。字符串常量池、Integer等常量池则是分担了运行时常量池的工作，\n" +
                    "\n" +
                    "在永久代移除后，字符串常量池也不再放在永久代了，但是也没有放到新的方法区---元空间里，而是留在了堆里（为了方便回收？）。运行时常量池当然是随着搬家到了元空间里，毕竟它是装类的重要信息的，有它的地方才称得上是方法区。");
        }

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(500);
        for (int i = 0; i< 500; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    localName.set(sb.toString());
                    System.out.println(Thread.currentThread().getName() + " finish processing threadLocal values...");
                   // localName.remove();// 注释后会出现oom
                }
            });
        }
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        localName = null;



        TestThreadLocal test = new TestThreadLocal("Tom");
//        Thread thread = Thread.currentThread();
//        System.out.println(thread.getName() + ": " + test.getString());

//        // 关闭强引用，gc后会回收threadLocalMap的ThreadLocal（通过弱引用）， Entry key会变成null
//        test.localName = null;
//        System.gc(); // 如果threadLocalMap对ThreadLocal是强引用， 无法回收（线程没有结束），如果线程一直跑，就会导致内存泄漏
//        // test.localName.remove();
//        System.out.println(thread);


        List<Thread> threadList = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++) {
            // 偶数线程
            if (i % 2 == 0) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(test.getString());
                    }
                });
                thread.start();

            } else {
                // 奇数线程
                int finalI = i;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        test.setString(Thread.currentThread().getName());
                        System.out.println(test.getString());
                        test.setString(String.valueOf(finalI));

                    }
                });
                thread.start();
            }

        }

//        TestThreadLocal test1 = test;
//
//        ThreadLocal tl = test.localName;
//        // 引用ThreadLocal的对象被回收了，但是ThreadLocalMap还引用着ThreadLocal(前提是线程还活着），
//        test = null;
//        System.gc();
//
//        try {
//            Thread.sleep(2000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(tl.get());
//        System.out.println(test1.getString());
    }
}
