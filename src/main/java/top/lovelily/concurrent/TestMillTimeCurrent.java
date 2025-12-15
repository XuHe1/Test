package top.lovelily.concurrent;

import java.util.concurrent.CountDownLatch;

public class TestMillTimeCurrent {

    public static void getCurrentMillTime() {
        String currentValue = String.valueOf(System.currentTimeMillis() + 60000);
        System.out.println(Thread.currentThread().getName() + ":" + currentValue);
    }


    public static void main(String[] args) {
        int nthread = 10;
        CountDownLatch latch = new CountDownLatch(nthread);
        for (int i=0; i < nthread;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getCurrentMillTime();
                }
            }).start();
            latch.countDown();
        }

    }
}
