package com.etianxia.concurrent;

import com.etianxia.ThreadPoolManager;

/**
 * @author h.xu
 * @create 2017-11-17 上午10:10
 **/

public class TestThreadPoolManager {
    public static void main(String[] args) {
        ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();
        while (true) {

            if (!threadPoolManager.hasCapable()) {
                do {
                    System.out.println("线程池忙，请等待。。。");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!threadPoolManager.hasCapable());

            }

            threadPoolManager.addExecuteTask(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " begin: ");
                        if ("pool-1-thread-16".equals(Thread.currentThread().getName())) {
                          //  throw new Exception("线程异常！"); // 线程池中某一线程抛异常，不影响其他线程
                            int a = 100/0;
                        }
                    } catch (RuntimeException e) {
                        //java.lang.ArithmeticException
                        e.printStackTrace();
                        //这里捕获异常，在线程池中就不会意外终止线程
                    }

                   // System.out.println(Thread.currentThread().getName() + " end: ");
                }
            }) );
        }

    }
}
