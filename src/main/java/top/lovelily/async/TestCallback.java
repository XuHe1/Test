package top.lovelily.async;

import java.util.function.Consumer;

/**
 * Desc: TestCallback
 * Author: xuhe
 * Date: 2020/8/10 6:10 下午
 * Version: 1.0
 */
public class TestCallback {
        public static void getSalary(Consumer<Double> callback) {
            double salary = 50_000.00;
            System.out.println("Get salary...");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 同步回调会阻塞
            callback.accept(salary);
            // 异步回调，不会阻塞线程
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(10000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    callback.accept(salary);
//                }
//            }, "Callback-Thread-1").start();

        }
        public static void main(String... args) {
            // here we are passing a consumer function
            // as an argument
//            getSalary((salary) -> {
//                System.out.println(Thread.currentThread().getName());
//                System.out.println("Gross salary :" + salary);
//            });

            // 主线程会阻塞等待回调
//            getSalary(new Consumer<Double>() {
//                @Override
//                public void accept(Double salary) {
//                    System.out.println(Thread.currentThread().getName());
//                    System.out.println("Gross salary :" + salary);
//                }
//            }.andThen(s -> {
//                System.out.println("稍后存银行！");
//            }));

                getSalary(new Consumer<Double>() {
                    @Override
                    public void accept(Double salary) {
                        // 同步回调
                            System.out.println(Thread.currentThread().getName());
                            System.out.println("Gross salary :" + salary);

                        // 异步回调
/*                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getName());
                                System.out.println("Gross salary :" + salary);
                            }
                        }).start();*/

                    }
                }.andThen(s -> {
                            System.out.println("存银行: " + s);
                }));


            System.out.println("MAIN END： Do other thing....");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程睡醒了！");
                }
            }).start();

        }
}
