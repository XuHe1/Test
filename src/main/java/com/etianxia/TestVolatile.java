package com.etianxia;

/**
 * @author h.xu
 * @create 2017-11-20 下午2:19
 **/

public class TestVolatile {
    public static volatile int race = 0;
    public static void increase() {
        race ++;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 20; i ++) {
          Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    increase();
                }
            });
          thread.setName("custom-thread-" + i);
          thread.start();

        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println(race);
    }
}
