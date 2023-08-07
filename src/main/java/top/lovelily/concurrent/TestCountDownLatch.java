package top.lovelily.concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
    public static void main(String[] args) {
        int nThread = 3;
        // 倒计时插销
        CountDownLatch latch = new CountDownLatch(nThread);
        for (int i = 0; i < nThread; i++ ) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " before---");
                        latch.await();// cause the thread wait,until countdown to zero  WAITING 状态
                        System.out.println(Thread.currentThread().getName() + " after---");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            });
            thread.setName("user-thread-" + i);
            thread.start(); // 直接启动阻塞了
            //thread.start();
            latch.countDown();    //jconsole: java.util.concurrent.CountDownLatch$Sync@4e6c5888上的WAITING
            //System.out.println(thread.getName() + " is in " + thread.getState());
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
