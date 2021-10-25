package top.lovelily.async;

import top.lovelily.User;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class TestCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5000));
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        CompletableFuture<User> task = CompletableFuture.supplyAsync(new Supplier<User>() {
            @Override
            public User get() {
                System.out.println("异步任务开始执行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("异步任务执行完毕");
                return new User(1, "xuhe");
            }
        },executor);

        //task.run();

        System.out.println("pre...");

        // 真正异步非阻塞(通知机制)
        task.thenAccept(user -> {
            System.out.println(user.getName());
        });
       // task.join();

        System.out.println("do other thing");

        executor.shutdown();

        System.out.println("after");

    }
}
