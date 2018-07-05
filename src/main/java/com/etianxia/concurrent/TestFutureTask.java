package com.etianxia.concurrent;

import com.etianxia.User;

import java.util.concurrent.*;

public class TestFutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<User> task = new FutureTask(new Callable() {
            @Override
            public User call() throws Exception {
                Thread.sleep(200l);
                System.out.println("generating...");
                return new User(1, "xuhe");
            }
        });

        //task.run();

        System.out.println("pre...");

        ExecutorService exec = Executors.newFixedThreadPool(5);
        exec.submit(task);

        System.out.println("do other thing");

        User user = task.get();
        System.out.println(user.getName());
        exec.shutdown();

        System.out.println("after");

    }
}
