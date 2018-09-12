package com.exception;

import com.etianxia.User;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author h.xu
 * @create 2018-02-05 下午5:08
 **/

public class Test {

    // StackOverflowError: stack
    public void recurse() {
        System.out.println("recurse call.");
        this.recurse();
    }

    /**
     * OutOfMemoryError: Java heap space
     *
     * -Xms1m -Xmx1m
     */
    public void heapLeakByObject() {
        List<User> userList = new ArrayList<>();
        while (true) {
            User user = new User(1, "xuhe", 28, 175, "Shanghai.China");
            userList.add(user);
        }
    }


    /**
     *  OutOfMemoryError: unable to create new native thread
     *  -Xss1m
     */

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                    }
                }
            });
            thread.start();
        }
    }


    /**
     * OutOfMemoryError: Java heap space
     *
     * 1.8后移除了permSize 放在heap
     *
     * http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html
     */
    public void permanOMM() {
        List<String> stringList = new ArrayList<>();
        int i = 0;
        while (true) {
            String str = String.valueOf(i++).intern();
            stringList.add(str);
        }
    }


    public void allocateByUnsafe() throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(1024 * 1024);
        }
    }


    public static void main(String[] args) throws IllegalAccessException {

        // Checked Exception: 非RuntimeException，IOException, InterruptedException
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //UncheckedException: RuntimeException, Error, and their subclasses
        Integer num = null;


        for (int i = 0; i < 10; i++) {
            try {
                //    return; // finally 仍然执行
                    //throw new NullPointerException("空指针");
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                // resources(implements AutoCloseable , Closeable) should be closed

                System.out.println("finally block executed.");
            }

        }

        Test test = new Test();
        //test.recurse();
        // OutOfMemoryError : stack  heap
        // StackOverflowError: stack
        //test.heapLeakByObject();
       // test.stackLeakByThread();
        //test.permanOMM();
        test.allocateByUnsafe();

    }
}