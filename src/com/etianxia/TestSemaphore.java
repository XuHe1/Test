package com.etianxia;

/**
 * @author h.xu
 * @create 2017-09-18 下午4:40
 **/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestSemaphore {

    public static void main(String[] args) {

        // 线程池
        //ExecutorService exec = Executors.newCachedThreadPool();
        ExecutorService exec = Executors.newFixedThreadPool(5);

        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);

        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            System.out.println(Thread.currentThread().getName());
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    System.out.println(NO);
                    try {
                        // 获取许可
                        semp.acquire();

                        System.out.println("Accessing: " + NO);
                        //System.out.println(Thread.currentThread().getName());
                        System.out.println("Available: " + semp.availablePermits());
                        //5 条线程挂起，占资源（Semaphore）
                        Thread.sleep((long) (Math.random() * 10000));

                        //Thread.sleep((long) (10000));
                        // 访问完后，释放
                        semp.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            };
			/*
			 * 不是立即执行，有个线程调度的过程，“在将来的某个时刻执行”
			 * Executes the given command at some time in the future.
			 */
            exec.execute(run);
            //run.run();
        }

        // 退出线程池

        exec.shutdown();

    }

}