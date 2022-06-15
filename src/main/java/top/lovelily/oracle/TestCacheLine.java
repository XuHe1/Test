package top.lovelily.oracle;

/**
 * 编写 cache friendly 代码， 现代系统 cache line 大小 64B
 * Desc: TestCacheLine
 * Author: xuhe
 * Date: 2020/5/29 4:33 下午
 * Version: 1.0
 */
public class TestCacheLine {
    static class Align {
        public volatile long p1, p2, p3;
    }

    static class T extends Align{
        private volatile long x;
    }

    static T[] arry = new T[2];
    static {
        arry[0] = new T();
        arry[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (long i = 0; i < 10000000L; i++) {
                    arry[0].x = i;
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (long i = 0; i < 10000000L; i++) {
                    arry[1].x = i;
                }
            }
        });
        long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start)/1000_000);
    }


}
