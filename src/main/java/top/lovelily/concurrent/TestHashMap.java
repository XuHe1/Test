package top.lovelily.concurrent;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * 并发场景调用get会进入死循环?? jdk1.8 之前, 1.7 就可以
 * 容量大于总量*负载因子发生扩容时会出现环形链表从而导致死循环
 */
public class TestHashMap {

    private static Map<Integer, Integer> map = new HashMap<>(2, 0.75f);
    static {
        map.put(5, 55);
    }

    private  static CountDownLatch latch = new CountDownLatch(300);
    public static void main(String[] args) throws InterruptedException {
        final HashMap<String, String> map = new HashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) { new Thread(new Runnable() {
                    @Override
                    public void run() {
                        map.put(UUID.randomUUID().toString(), ""); }
                }, "ftf" + i).start(); }
            }
        }, "ftf");
        t.start();
        t.join();

        System.out.println(map.entrySet().iterator().next());

//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//              map.put(7, 77);
//            }
//        });
//        thread1.start();
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                map.put(3, 33);
//            }
//        });
//        thread2.start();
//
//       // System.out.println(map.get(11));
//
//        long time = System.currentTimeMillis();
//        Map<Long, String> map = Maps.newHashMap();
//        map.put(time, "hello");
//        long t1 = time; //hashCode， 数值的hashcode跟值相关
//        System.out.println(map.get(t1));
    }
}
