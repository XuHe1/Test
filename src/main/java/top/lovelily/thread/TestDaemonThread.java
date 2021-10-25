package top.lovelily.thread;

import java.time.LocalTime;

/**
 * Desc: TestDaemonThread
 * Author: xuhe
 * Date: 2019/11/20 5:21 下午
 * Version: 1.0
 */

public class TestDaemonThread {
    public static void main(String[] args) {
        System.out.println("main start...");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("non daemon thread start");
                try {
                    Thread.sleep(2000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("non daemon thread end");
            }
        });
        t1.start();

        Thread t = new TimerThread();
        // 在 start 前使用，当所有用户线程结束后，程序会退出，同时杀死所有守护线程！
        t.setDaemon(true);
        t.start();

        System.out.println("main end...");

    }
}

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
