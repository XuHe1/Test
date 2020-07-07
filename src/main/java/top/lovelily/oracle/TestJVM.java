package top.lovelily.oracle;

import top.lovelily.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author h.xu
 * @create 2018-02-05 下午5:08
 **/

public class TestJVM {
    private byte[] b = new byte[1024*100]; //100KB

    // StackOverflowError: stack
    public void recurse() {
        System.out.println("recurse call.");
        this.recurse();
    }

    /**
     * OutOfMemoryError: Java heap space
     *
     * -Xms5m -Xmx5m
     */
    public void heapLeakByObject() throws InterruptedException {
       List<TestJVM> list = new ArrayList<>();
        while (true) {
            list.add(new TestJVM());
            Thread.sleep(50);
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
//        String str = "";
//        str.intern();
        List<String> stringList = new ArrayList<>();
        int i = 0;
        while (true) {
            String str = String.valueOf(i++).intern();
            stringList.add(str);
        }
    }


    public void allocateByUnsafe() throws IllegalAccessException {
        // https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html

//        Unsafe unsafe1 = Unsafe.getUnsafe();
//        unsafe1.allocateMemory(1024 * 1024);
//
//        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
//        unsafeField.setAccessible(true);
//        Unsafe unsafe = (Unsafe) unsafeField.get(null);
//        while (true) {
//            unsafe.allocateMemory(1024 * 1024);
//        }
    }

    /**
     *  The {@code Throwable} class is the superclass of all errors and
     *  exceptions in the Java language.
     *  Only objects that are instances of this
     *  class (or one of its subclasses) are thrown by the Java Virtual Machine or
     *  can be thrown by the Java {@code throw} statement
     *
     * 用户自定义异常，需要用户手动抛出，系统定义异常，由JVM自动抛出，即使没有通过代码抛出
     *
     * @throws ArrayIndexOutOfBoundsException
     */
    public void testThrowable() throws ArrayIndexOutOfBoundsException{
        try {
            int a[] = new int[5];
            int b = 2/0; // jvm 抛出
            a[5] = 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e; // 代码抛出
        }

    }


    public static void main(String[] args) throws IllegalAccessException, InterruptedException {

        // Checked Exception: 非RuntimeException，IOException, InterruptedException
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //UncheckedException: RuntimeException, Error, and their subclasses
//        Integer num = null;


//        for (int i = 0; i < 10; i++) {
//            try {
//                //    return; // finally 仍然执行
//                    //throw new NullPointerException("空指针");
//            } catch (Exception e) {
//                System.out.println(e);
//            } finally {
//                // resources(implements AutoCloseable , Closeable) should be closed
//
//                System.out.println("finally block executed.");
//            }
//
//        }

        // TestJVM test = new TestJVM();
        //test.recurse();
        // OutOfMemoryError : stack  heap
        // StackOverflowError: stack
       // test.heapLeakByObject();

        List<TestJVM> list = new ArrayList<>();
        while (true) {
            list.add(new TestJVM());
            Thread.sleep(50);
        }

//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // test.stackLeakByThread();
        //test.permanOMM();
       // test.allocateByUnsafe();
//        try {
//            test.testThrowable();
//        } catch (Exception e) {
//            System.out.println(e.getClass().getName());
//        }


    }
}
