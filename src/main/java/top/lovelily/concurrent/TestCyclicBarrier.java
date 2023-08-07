package top.lovelily.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Desc: CyclicBarrier 可循环使用的屏障，
 * Author: Xu He
 * created: 2021/7/16 16:51
 */

public class TestCyclicBarrier {
    public static void main(String[] args) {

        int nThread = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(nThread, new Runnable() {
            @Override
            public void run() {
                // 屏障触发时执行，由最后一个到达屏障的线程触发并执行
                System.out.println(Thread.currentThread().getName() +"-触发屏障，开始执行");
            }
        });

        for (int i = 0; i < nThread; i++) {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    System.out.println("任务"+ finalI + "已就绪");
                    try {
                        cyclicBarrier.await(); // ReentrantLock.condition 实现,最后一个线程到达，调用signal
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    // 屏障后执行
                    System.out.println("正在执行任务"+ finalI);
                }
            });
            thread.setName("Thread-"+i);
            thread.start();
        }
    }
}
