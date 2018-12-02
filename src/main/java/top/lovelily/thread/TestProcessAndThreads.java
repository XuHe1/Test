package top.lovelily.thread;

import java.nio.ByteBuffer;

/**
 * @author h.xu
 * @create 2017-10-31 下午5:59
 **/

public class TestProcessAndThreads {
    public static  int count = 0;
    public static void main(String[] args) throws InterruptedException {
        //String str = "46,EISGov_DT1LoCurr_mp,[s],0x4C343C,4,SLONG,,"; // split length 6
        String str = "46,EISGov_DT1LoCurr_mp,[s],0x4C343C,4,SLONG, , ";
        System.out.println(str.split(",").length);

        Thread.sleep(20000l);

        byte[] b = new byte[1024*10];

        System.out.println("main begin");

        //
        Thread thread = new Thread(null, new Runnable() {
            @Override
            public void run() {
                count = count + 1;
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread process...");
            }
        }, "MY_THREAD", 1024*10l);

        thread.start();
        thread.join(); // main 线程会等待子线程执行
        System.out.println("main end");
        System.out.println(thread.getState()); // 线程执行完，状态终止
        System.gc();
        System.out.println(thread);
//        ByteBuffer.allocateDirect(128);
//        ByteBuffer.allocate(128);

        while (true) {

        }
       // System.exit(0);  //强制退出jvm进程，子线程也被强制退出
        // Thread.currentThread().stop(); // 关闭main线程，下面代码不再执行，但子线程不受main线程影响，会继续执行，之后退出jvm进程
        // System.out.println(Thread.currentThread().getName());


    }
}
