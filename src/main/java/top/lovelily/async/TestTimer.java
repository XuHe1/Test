package top.lovelily.async;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();

        for (int i = 0; i < 10; i++) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " do something At " + System.currentTimeMillis());
                    int a = 10/0; // 一旦某个task出现异常,其他task不再执行,因为Timer是单线程的
                }
            };

            timer.schedule(task,1000);
        }


    }
}
