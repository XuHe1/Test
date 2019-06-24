package top.lovelily.concurrent;

/**
 * @author h.xu
 * @create 2017-11-17 上午10:10
 **/

public class TestThreadPoolManager {
    public static void main(String[] args) {
        ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();
        while (true) {

            if (!threadPoolManager.hasCapable()) {
                do {
                    System.out.println("线程池忙，请等待。。。");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!threadPoolManager.hasCapable());

            }

            threadPoolManager.addExecuteTask(new Thread(new Runnable() {
                @Override
                public void run() {
                    //try {

                        if ("pool-1-thread-1".equals(Thread.currentThread().getName())) {
                            System.out.println(Thread.currentThread().getName() + " begin: ");
                          //  throw new Exception("线程异常！"); // 线程池中某一线程抛异常，不影响其他线程
                            int a = 100/0;
                        }
                  //  } catch (RuntimeException e) {
                        //java.lang.ArithmeticException
                    //    e.printStackTrace();
                        //这里捕获异常，在线程池中的线程就不会被回收
                    //}

                   // System.out.println(Thread.currentThread().getName() + " end: ");
                }
            }) );
        }

    }
}
