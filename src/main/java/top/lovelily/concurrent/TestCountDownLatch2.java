package top.lovelily.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCountDownLatch2 {
    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    Thread.sleep(2000l);
                    System.out.println(finalI);
                } catch (Exception e) {

                } finally {
                    latch.countDown();
                    ;
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {

        }

        System.out.println(executorService);
        // 如果不关闭线程池，线程池核心线程会阻塞，从队列中拿任务，main线程虽然结束，但是 jvm 不会退出，继续运行线程池线程
        try {
            executorService.shutdown();
        } catch (Exception e) {
        }
        System.out.println(executorService);
        System.out.println("end"); // 执行了，但是线程没有终止
        System.out.println(Thread.currentThread().getState());
        //return; // 如果线程池不关闭，main线程没有终止，需要return
    }
}
