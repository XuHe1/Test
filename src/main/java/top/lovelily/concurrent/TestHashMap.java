package top.lovelily.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 并发场景调用get会进入死循环??
 * 容量大于总量*负载因子发生扩容时会出现环形链表从而导致死循环
 */
public class TestHashMap {

    private static Map<Integer, Integer> map = new HashMap<>(2, 0.75f);
    static {
        map.put(5, 55);
    }

    private  static CountDownLatch latch = new CountDownLatch(300);
    public static void main(String[] args) {
//        int i;
//        for (i = 0; i < 300; i++) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        latch.await();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    map.put(7, 77);
//                    map.put(3, 33);
//                    System.out.println(System.currentTimeMillis() + ": " + map.get("test1"));
//                }
//            });
//            thread.start();
//            latch.countDown();
//        }
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
              map.put(7, 77);
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                map.put(3, 33);
            }
        });
        thread2.start();

        System.out.println(map.get(11));
    }
}
