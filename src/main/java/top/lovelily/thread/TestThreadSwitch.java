package top.lovelily.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Desc: TestThreadSwitch
 * Author: xuhe
 * Date: 2020/4/1 1:47 下午
 * Version: 1.0
 */
public class TestThreadSwitch {

    public static void main(String[] args) {
        // 无锁的情况下， 线程是交替执行的， 无论是不是死循环
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("hello");
                }
            }
        });
        t.setName("thread1");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("world");
                }
            }
        });
        t1.setName("thread2");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fileInputStream = new FileInputStream(new File("/Users/xuhe/Documents/bookmarks_2020_3_14.html"));
                    byte[] unit = new byte[20];
                    int n = 0;
                    while ((n = fileInputStream.read(unit)) != -1) {
                        System.out.println(new String(unit, "UTF-8"));
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.setName("thread3");

        //t.start();
        t1.start();
        t2.start();


    }
}
