package top.lovelily.thread;

import java.util.concurrent.Exchanger;

/**
 * Desc: TestExchange
 * Author: xuhe
 * Date: 2020/7/21 11:12 上午
 * Version: 1.0
 */
public class TestExchange {
    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();
        try {
            exchanger.exchange("hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                Thread.sleep(2000l);
               String s = (String) exchanger.exchange("hello");
                System.out.println(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
