package top.lovelily.thread;

import java.util.concurrent.TimeUnit;

/**
 * Desc: TestInterrupt
 * Author: xuhe
 * Date: 2020/11/9 5:51 下午
 * Version: 1.0
 */
public class TestInterrupt {
        public static void main(String[] args) throws Exception {
            // sleepThread不停的尝试睡眠
            Thread sleepThread = new Thread(new SleepRunner(), "SleepThread"); sleepThread.setDaemon(true);
            // busyThread不停的运行
            Thread busyThread = new Thread(new BusyRunner(), "BusyThread"); busyThread.setDaemon(true);
            sleepThread.start();
            busyThread.start();
            // 休眠5秒，让sleepThread和busyThread充分运行
            TimeUnit.SECONDS.sleep(5);
            sleepThread.interrupt();
            busyThread.interrupt();
            System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted() + ", State: " + sleepThread.getState());
            System.out.println("BusyThread interrupted is " + busyThread.isInterrupted() + ", State: " + sleepThread.getState()); // 防止sleepThread和busyThread立刻退出
            Thread.sleep(2000l);
        }

        static class SleepRunner implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } }
        static class BusyRunner implements Runnable {
            @Override
            public void run() {
                while (true) {
                } }
}
}
