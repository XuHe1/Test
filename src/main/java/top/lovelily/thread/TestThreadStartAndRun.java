package top.lovelily.thread;

public class TestThreadStartAndRun {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "任务执行");
            }
        });
        thread.setName("My-Thread");
        // My-Thread任务执行
        //thread.start();
        // main任务执行
        thread.run();


    }
}
