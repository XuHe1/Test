package top.lovelily.concurrent;

import top.lovelily.User;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @author h.xu
 * @create 2018-01-31 下午5:13
 *
 * CountDownLatch 模拟高并发情况下，缓存穿透
 **/

public class MultiThreadClient {
    private static CountDownLatch latch = new CountDownLatch(10);
    private static CountDownLatch latch1 = new CountDownLatch(10); // main 线程等待子线程

    @Test
    public void testSingleThread() {
        UserCacheService userCacheService = new UserCacheService();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    User user = userCacheService.getUser("xuhe");
                    System.out.println(Thread.currentThread().getName() + " : " + System.currentTimeMillis());
                    }
            });
            thread.start();
//            try {
//                thread.join(); // 多个线程串行执行
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

    }

    @Test
    public void testMultiThread() {
        // todo: 测试多线程更新数据库同一条记录
        UserCacheService userCacheService = new UserCacheService();
        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        //User user = userCacheService.getUser3("xuhe");
                        //User user = userCacheService.getCacheUser("xuhe");
                        User user = userCacheService.getUserAsync("xuhe");
                        latch1.countDown();

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            latch.countDown();
        }


        try {
            latch1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished..");

    }

    /**
     * https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html
     */
    @Test
    public void testJoin() {
        UserCacheService userCacheService = new UserCacheService();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    User user = userCacheService.getUser3("xuhe");
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    public void testJoinAndLatch() {
        UserCacheService userCacheService = new UserCacheService();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    User user = userCacheService.getUser3("xuhe");
                }
            });


            try {
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            latch.countDown();

        }

        System.out.println(Thread.currentThread().getName());
    }
}
