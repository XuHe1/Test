package top.lovelily.thread;

/**
 * Desc: TestThreadBase
 * Author: xuhe
 * Date: 2018/10/13 下午3:43
 * Version: 1.0
 */
public class TestThreadBase {
    private static int start = 0;
    // 状态码
    private static Integer state = new Integer(1);

    // 奇数
    public static void printOdd() throws InterruptedException {
        synchronized (state) { // 拥有对象监视器
            while (start < 100) {
                if (start % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + ": " + start);
                    start ++;
                   // Thread.sleep(1000l);
                    state.notify(); // 唤醒所有等待在该对象monitor的线程
                } else {
                    state.wait(); // 当前线程等待，并释放对象monitor，直到另一个线程拿到对象monitor后调用notify() 或notifyAll()
                }
            }

        }

    }

    // 偶数
    public static void printEven() throws InterruptedException {
        synchronized (state) {
            while (start < 100) {
                if (start % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + start);
                    start ++;
                    // sleep只是让出CPU，并不释放锁（monitor）
                    // Thread.sleep(1000); // TIMED_WAITING， Odd线程 BLOCKED
                    state.notify();
                } else {
                   // while (true)
                      state.wait();  // WAITING
                }
            }

        }

    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printEven();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printOdd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("Event");
        t2.setName("Odd");
        t1.start();
        t2.start();
        System.out.println(t1.getState());
        t1.join();
        t2.join();
        System.out.println("MAIN");

        /**
         *             WAITING(wait())
         *                 ^
         *                 |
         *                 |
         *        start()  |     sleep()
         *   NEW -----> RUNNABLE-----> TIMED_WAITING
         *                 |
         *                 |
         *                 V
         *             BLOCKED(waiting for monitor)
         *
         */

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);  // TIMED_WAITING
                } catch (InterruptedException e) {

                }
            }
        });
        System.out.println(thread.getState());  // NEW
        thread.start(); // RUNNABLE
        System.out.println(thread.getState());
        Thread.sleep(2000l);
        System.out.println(thread.getState());

    }
}
