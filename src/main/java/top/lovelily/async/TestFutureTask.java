package top.lovelily.async;

import top.lovelily.User;

import java.util.concurrent.*;

public class TestFutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<User> task = new FutureTask(new Callable() {
            @Override
            public User call() throws Exception {
                System.out.println("Sleeping...");
                Thread.sleep(200l);
                System.out.println("generating...");
                return new User(1, "xuhe");
            }
        });

        //task.run();

        System.out.println("pre...");

        ExecutorService exec = Executors.newFixedThreadPool(5);

        // submit & execute
        Future<User> future = (Future<User>) exec.submit(task);
        exec.execute(task); // worker-thread直接

        System.out.println("do other thing");

      //  User user = task.get(); // call方法已经在执行了,get阻塞直到处理完毕, java8提供了CompletableFuture真正实现了异步获取处理结果
      //  System.out.println(user.getName());

        System.out.println(future.get().getName());

        exec.shutdown();

        System.out.println("after");

    }
}
