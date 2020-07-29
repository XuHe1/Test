package top.lovelily.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Desc: TestVolatile001
 * Author: xuhe
 * Date: 2020/7/7 10:04 上午
 * Version: 1.0
 */
public class TestVolatile001 {
    // private static boolean isStop = false;
    //  private boolean isStop = false;
    private  boolean isStop = false;

    // 同一个对象调用下面两个方法使用的是同一把锁；
    // synchronized 也能保证可见性：进入synchronized块，读取全局变量会从主存读取；
    public synchronized boolean isStop() {
        return this.isStop;
    }
    // synchronized 也能保证可见性：退出synchronized块，本地内存对全局变量副本的写会强制刷新到主存；lock cmpxchg 指令直接操作的是主存？
    public synchronized void setStop(boolean isStop) {
        this.isStop = isStop;
    }

    public static void main(String[] args) {
        TestVolatile001 t1 = new TestVolatile001();

        new Thread(() -> {
            while (true) {
                if (t1.isStop()) {  // t1.isStop()
                    System.out.println("结束");
                    return;
                }
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
            //t1.isStop = true;
            t1.setStop(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
