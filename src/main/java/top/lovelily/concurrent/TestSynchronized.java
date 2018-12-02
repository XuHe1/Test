package top.lovelily.concurrent;

/**
 * 对象锁：包含方法锁、对象锁，作用于某一具体对象
 * 类锁：作用于类，作用于所有对象
 * Desc: TestSynchronized
 * Author: xuhe
 * Date: 2018/9/12 下午12:41
 * Version: 1.0
 */
public class TestSynchronized {
    private static int count = 0;

    // 方法锁(对象锁)
    public synchronized void increase() {
        count ++;
    }

    // 类锁
    public synchronized static int getCount() {
        return count;
    }

    public int getCount1() {
        // 类锁
        synchronized (TestSynchronized.class) {
            return count;
        }
    }

    public int getCount2() {
        // 对象锁
        synchronized (this) {
            return count;
        }
    }

}
