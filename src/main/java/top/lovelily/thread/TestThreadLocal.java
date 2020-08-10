package top.lovelily.thread;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

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
    private ThreadLocal<String> localName = new ThreadLocal<>();

    public TestThreadLocal(String name) {
        localName.set(name);
    }

    private String getString() {
        return localName.get();
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
            if (i % 2 == 0) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println(test.getString());

                    }
                });
                thread.start();
                System.out.println(thread);


            } else {
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

           // System.out.println(ClassLayout.parseInstance(test.localName).toPrintable());

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
