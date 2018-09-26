package com.etianxia.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Desc: TestConcurrentHashMap
 * Author: xuhe
 * Date: 2018/9/25 下午4:51
 * Version: 1.0
 */

public class TestConcurrentHashMap {

    static Map<String, Integer> counter = new ConcurrentHashMap();
    public static void main(String[] args) throws InterruptedException {
        counter.put("stock1", 0);
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    counter.put("stock1", count.getAndIncrement());
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("result is " + counter.get("stock1"));
    }
}