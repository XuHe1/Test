package top.lovelily.async;

import top.lovelily.User;

import java.util.concurrent.*;

public class TestCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5000));
        CompletableFuture<User> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("Sleeping...");
            try {
                Thread.sleep(200l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("generating...");
            return new User(1, "xuhe");
        }, executor);

        //task.run();

        System.out.println("pre...");

        // 真正异步非阻塞
        task.thenAccept(user -> {
            System.out.println(user.getName());
        });

        System.out.println("do other thing");

        executor.shutdown();

        System.out.println("after");

    }
}
