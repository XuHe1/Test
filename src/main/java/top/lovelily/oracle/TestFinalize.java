package top.lovelily.oracle;

import top.lovelily.User;

/**
 * Desc: TestFinalize
 * Author: Xu He
 * created: 2021/10/22 17:46
 */

public class TestFinalize {
    public static void main(String[] args) {
        System.out.println("hello");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                System.out.println(user);

                 throw new OutOfMemoryError("内存溢出了");

            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 线程OOM 后会终止，系统自动会触发 gc
        System.gc();

        System.out.println("hello1");

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("销毁了");
    }
}
