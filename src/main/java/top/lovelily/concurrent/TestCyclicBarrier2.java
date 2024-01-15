package top.lovelily.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 3 个线程交替打印 ABC
 */
public class TestCyclicBarrier2 {
    // 共享计数器
    private static int sharedCounter = 0;
    public static void main(String[] args) {
        // 打印的内容
        String printString = "ABC";
        // 定义循环栅栏
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("一轮打印开始");
        });
        // 执行任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < printString.length(); i++) {
                   // System.out.println(Thread.currentThread().getName() + "到达屏障！");
                    try {
                        // 等待 3 个线程都打印一遍之后，继续走下一轮的打印
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    synchronized (this) {
                        System.out.println(Thread.currentThread().getName());
                        sharedCounter = sharedCounter > 2 ? 0 : sharedCounter; // 循环打印
                        System.out.println(printString.toCharArray()[sharedCounter++]);
                    }
                }
            }
        };
        // 开启多个线程
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}

