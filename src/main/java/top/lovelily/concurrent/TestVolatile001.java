package top.lovelily.concurrent;

import java.util.concurrent.TimeUnit;

/**
 *
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp -XX:CompileCommand=dontinline,*TestVolatile001.setIsStop
 * Desc: TestVolatile001
 * Author: xuhe
 * Date: 2020/7/7 10:04 上午
 * Version: 1.0
 */
public class TestVolatile001 {

    private static volatile boolean  isStop = false;

    public synchronized void setIsStop() {
        /**
         *  0x0000000115064f53: lock addl $0x0,(%rsp)     ;*putstatic isStop
         *                                                 ; - top.lovelily.concurrent.TestVolatile001::<clinit>@1 (line 13)
         */
        //isStop = true;  // 反汇编后： lock addl$0x0，(%rsp) 指令把修改同步到内存，同时使其他cpu缓存失效
        int a = 1;
    }

//    // private static boolean isStop = false;
//    //  private boolean isStop = false;
//    private  boolean isStop = false;
//
//    // 同一个对象调用下面两个方法使用的是同一把锁；
//    // synchronized 也能保证可见性：进入synchronized块，读取全局变量会从主存读取；
//    public synchronized boolean isStop() {
//        return this.isStop;
//    }
//    // synchronized 也能保证可见性：退出synchronized块，本地内存对全局变量副本的写会强制刷新到主存；lock cmpxchg 指令直接操作的是主存？
    public synchronized void setStop(boolean isStop) {
        this.isStop = isStop;
    }

    public static void main(String[] args) {
        new TestVolatile001().setIsStop();
//        TestVolatile001 t1 = new TestVolatile001();
//
//        new Thread(() -> {
//            while (true) {
//                if (t1.isStop()) {  // t1.isStop()
//                    System.out.println("结束");
//                    return;
//                }
//            }
//        }).start();
//
//        try {
//            TimeUnit.SECONDS.sleep(3);
//            //t1.isStop = true;
//            t1.setStop(true);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
