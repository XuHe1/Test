package top.lovelily.thread;

import java.time.LocalTime;

/**
 * Desc: TestDaemonThread
 * Author: xuhe
 * Date: 2019/11/20 5:21 下午
 * Version: 1.0
 */

class TimerThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println(LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

public class TestDaemonThread {
    public static void main(String[] args) {
        System.out.println("hello");
        Thread t = new TimerThread();
        t.setDaemon(true);
        t.start();
    }
}
