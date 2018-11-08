package com.etianxia.thread;

/**
 * Desc: TestThreadBase
 * Author: xuhe
 * Date: 2018/10/13 下午3:43
 * Version: 1.0
 */
public class TestThreadBase {
    private static int start = 0;
    // 状态码
    private static Integer state = new Integer(1);

    // 奇数
    public static void printOdd() throws InterruptedException {
        synchronized (state) { // 拥有对象监视器
            while (start < 100) {
                if (start % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + ": " + start);
                    start ++;
                    state.notify(); // 唤醒所有等待在该对象monitor的线程
                } else {
                    state.wait(); // 当前线程等待，并释放对象monitor，直到另一个线程拿到对象monitor后调用notify() 或notifyAll()
                }
            }

        }

    }

    // 偶数
    public static void printEven() throws InterruptedException {
        synchronized (state) {
            while (start < 100) {
                if (start % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + start);
                    start ++;
                    state.notify();
                } else {
                    state.wait();
                }
            }

        }

    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printEven();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printOdd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("Event");
        t2.setName("Odd");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("MAIN");
    }
}
