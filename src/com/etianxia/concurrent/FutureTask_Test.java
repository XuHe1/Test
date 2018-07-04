package com.etianxia.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTask_Test {
    public static void main(String args[]) throws Exception {
        FutureTask<String> futureTask1 = new FutureTask<String>(new MyCallable(1000));
        FutureTask<String> futureTask2 = new FutureTask<String>(new MyCallable(2000));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        System.out.println("FutureTask1 output=" + futureTask1.get());
        System.out.println("FutureTask2 output=" + futureTask2.get());

        executor.shutdown();
    }

    static class MyCallable implements Callable<String> {
        private long waitTime;

        public MyCallable(int timeInMillis) {
            this.waitTime = timeInMillis;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(waitTime);

            return Thread.currentThread().getName();
        }
    }
}