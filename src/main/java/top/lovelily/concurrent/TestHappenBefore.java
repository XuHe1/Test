package top.lovelily.concurrent;

import top.lovelily.User;

/**
 * Desc: TestHappenBefore
 * ref: https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.4
 * A happens-before B:
 *  A产生的影响对B可见，影响包括：对共享内存的修改，发送了消息，调用了方法，与时间上先后没有关系，happen-before是判断线程安全和数据是否存在竞争的依据。
 *  1.  If x and y are actions of the same thread and x comes before y in program order, then hb(x, y).
 *
 *  2. There is a happens-before edge from the end of a constructor of an object to the start of a finalizer (§12.6) for that object.
 *
 *  3. If an action x synchronizes-with a following action y, then we also have hb(x, y).
 *
 *  4. If hb(x, y) and hb(y, z), then hb(x, z).
 *
 * Author: xuhe
 * Date: 2018/11/29 3:18 PM
 * Version: 1.0
 */
public class TestHappenBefore {
    private static int count = 0;
    private static User user = new User(1, "zhangsan");

    public static void main(String[] args) {

        // 同一个线程内部，书写在前面的代码happen-before 后面的代码，
       Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                int j = 2;   // 由于指令重排， 可能先执行， 但不违背 happen-before 规则
                // 共享变量
                count = 1;
                j = count;
                System.out.println(j);
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 调用方法
                user.setName("lisi");
                String name = user.getName();
                System.out.println(name);
            }
        });
       thread.start(); // start()先行发生于线程其他的操作
        try {
            thread.join(); // 调用join()方法的线程里的任意操作先行发生于main线程从join()方法成功返回（继续后续的代码）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()); // 主线程执行完后，仍会执行子线程，对操作系统而言，并不存在父子线程关系
    }

}
