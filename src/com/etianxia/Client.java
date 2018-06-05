package com.etianxia;

import java.util.concurrent.CountDownLatch;

/**
 * @author h.xu
 * @create 2018-01-25 上午10:29
 **/

public class Client {
    private CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread();
        }
    }



}
