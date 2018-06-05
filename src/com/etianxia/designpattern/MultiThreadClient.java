package com.etianxia.designpattern;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author h.xu
 * @create 2018-01-31 下午5:13
 *
 * CountDownLatch 模拟高并发情况下，缓存穿透
 **/

public class MultiThreadClient {
    private static CountDownLatch latch = new CountDownLatch(1);


    @Test
    public void testMultiThread() {
        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        TestDoubleCheckSingleton.getInstance();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        latch.countDown();


        //
        try {
            Thread.sleep(15000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
