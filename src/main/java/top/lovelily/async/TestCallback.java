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

            callback.accept(salary);
            // call back our callback function in a thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    callback.accept(salary);
                }
            }, "Asyn-Thread-1").start();

        }
        public static void main(String... args) {
            // here we are passing a consumer function
            // as an argument
//            getSalary((salary) -> {
//                System.out.println(Thread.currentThread().getName());
//                System.out.println("Gross salary :" + salary);
//            });

            getSalary(new Consumer<Double>() {
                @Override
                public void accept(Double salary) {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("Gross salary :" + salary);
                }
            }.andThen(s -> {
                System.out.println("稍后存银行！");
            }));
        }
}
