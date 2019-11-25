package top.lovelily.designpattern;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author h.xu
 * @create 2018-01-31 下午5:13
 *
 * CountDownLatch 模拟高并发情况下，缓存穿透
 **/

public class MultiThreadClient {
    private static CountDownLatch latch = new CountDownLatch(1);


    @Test
    public void testMultiThread() {
        for (int i = 0; i < 100; i++) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        // System.out.println(TestDoubleCheckSingleton.getInstance()); // 单例
                        // System.out.println(TestDoubleCheckSingleton.getInstance1()); // 非单例
                       // System.out.println(TestInternalClass.getInstance());
                        //System.out.println(TestEagerMode.getInstance());
                        try {
                            //Class.forName("top.lovelily.designpattern.TestEagerMode"); // 加载类并初始化（静态变量和静态代码块
                            ClassLoader classLoader = this.getClass().getClassLoader();
                            Class clazz = classLoader.loadClass("top.lovelily.designpattern.TestEagerMode");
                            clazz.newInstance();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                        //  System.out.println(TestDoubleCheckSingleton.lock());
                        // TestDoubleCheckSingleton.getInstance2();
                        //System.out.println(TestSnowFlake.generateSn("B"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        latch.countDown();


        //
        try {
            Thread.sleep(20000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
