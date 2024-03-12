package top.lovelily.concurrent;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始执行");
                LockSupport.park();
                System.out.println("结束执行");

            }
        });
        t1.start();

        //LockSupport.unpark(t1);
       // t1.interrupt(); // 都会唤醒线程


    }
}
