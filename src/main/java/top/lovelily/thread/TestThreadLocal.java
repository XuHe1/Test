package top.lovelily.thread;

/**
 * Desc: TestThreadLocal， 不同线程数据隔离，同线程多方法间数据共享
 * Author: xuhe
 * Date: 2019/4/29 9:55 PM
 * Version: 1.0
 */
public class TestThreadLocal {

    private ThreadLocal<String> localName = new ThreadLocal<>();

    public TestThreadLocal(String name) {
        localName.set(name);
    }

    private String getString() {
        return localName.get();
    }

    private void setString(String string) {
        localName.set(string);
    }


    public static void main(String[] args) {
        TestThreadLocal test = new TestThreadLocal("Tom");
        System.out.println(Thread.currentThread().getName() + ": " + test.getString());
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(test.getString());
                    }
                });
                thread.start();
            } else {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        test.setString(Thread.currentThread().getName());
                        System.out.println(test.getString());

                    }
                });
                thread.start();
            }


        }
    }
}
